package com.snj.utils;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

public class ScreenRecording extends ScreenRecorder {
	public static ScreenRecording screenRecorder;
	public String name;

	public ScreenRecording(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
			Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;

	}

	@Override
	protected File createMovieFile(Format fileFormat) {
		SimpleDateFormat dateFormat = null;
		try {
			if (!movieFolder.exists()) {
				movieFolder.mkdirs();
			} else if (!movieFolder.isDirectory()) {
				throw new IOException("\"" + movieFolder + "\" is not a directory.");
			}
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(movieFolder,
				name + "_" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
	}

	/**
	 * Start video recording and store it in Automation_Videos folder inside Reports
	 * folder
	 * 
	 * @author sanojs
	 * @since 23/01/2023
	 * @param testCaseName
	 * @throws Exception
	 */
	public static void startVideoRecording(String testCaseName) {
		try {
			if (!new File(System.getProperty("user.dir") + "\\Reports").exists()) {
				(new File(System.getProperty("user.dir") + "\\Reports")).mkdir();
			}
			if (!new File(System.getProperty("user.dir") + "\\Reports\\Automation_Videos\\").exists()) {
				new File(new File(System.getProperty("user.dir")), "\\Reports\\Automation_Videos\\").mkdirs();
			}
			File file = new File(System.getProperty("user.dir") + "\\Reports\\Automation_Videos\\");
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = screenSize.width;
			int height = screenSize.height;
			Rectangle captureSize = new Rectangle(0, 0, width, height);
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration();
			screenRecorder = new ScreenRecording(gc, captureSize,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, file, testCaseName);
			screenRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stop video recording
	 * 
	 * @author sanojs
	 * @since 23/01/2023
	 * @throws Exception
	 */
	public static void stopVideoRecording() {
		try {
			screenRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}