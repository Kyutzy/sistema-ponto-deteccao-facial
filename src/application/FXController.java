package application;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.Color;

import java.util.HashMap;

	
public class FXController {
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
	private VideoCapture capture;
	@FXML
	private Button start_btn;
	@FXML
	private Button teste_btn;
	@FXML
	private ImageView currentFrame;
	@FXML
	private ImageView fotoPessoa;
	@FXML
	private TextField data;
	@FXML
	private TextField hora;
	@FXML
	private TextField nome;
	private ScheduledExecutorService timer;
	CascadeClassifier facedetector = new CascadeClassifier();
	static MatOfByte buffer;
	static Mat frame = null;
	static Mat foto;
	static Boolean facefound;
	static Rect corte;
	static HashMap<Mat, Pessoa> cadastrados = new HashMap<Mat, Pessoa>();
	
	@FXML
	protected void startCamera(ActionEvent event) {
			capture = new VideoCapture();
			facedetector.load("haarcascade_frontalface_alt.xml");
			System.out.println(String.format("%s\\notfound.jpg", System.getProperty("user.dir")));
			fotoPessoa.setImage(new Image("file:notfound.jpg"));
			if (this.capture.isOpened()) {
				this.capture.release();
				System.exit(0);
			} else {
				this.capture.open(0);
				Runnable frameGrabber = new Runnable() {

					@Override
					public void run() {
						frame = new Mat();
						capture.read(frame);
						Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2RGBA);
						buffer = new MatOfByte();
						Imgcodecs.imencode(".png", frame, buffer);
						currentFrame.setImage(new Image(new ByteArrayInputStream(buffer.toArray())));

						
					}
					
				};
				Runnable verificador = new Runnable() {

					@Override
					public void run() {
						MatOfRect detections = new MatOfRect();
						try {
						System.out.println("fwejio");
						facedetector.detectMultiScale(frame, detections);
						for (Rect rect : detections.toArray()) {
						corte = new Rect(rect.x, rect.y, rect.height, rect.width);
						foto = new Mat(frame, corte);
						System.out.println("aqui tambem");
						Imgcodecs.imwrite("C:\\Users\\Teste\\Desktop\\teswetg\\seliga.jpg", foto);
						runnable.run();
						}
						} catch (Exception e) {
							//System.out.println(e);
						}
						
						
					}
					
				};
				this.timer = Executors.newScheduledThreadPool(2);
				this.timer.scheduleAtFixedRate(verificador, 2, 3, TimeUnit.SECONDS);
	            this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);  
			}
	}
	public Runnable runnable = ()->{
		Mat compare1 = new Mat();
		Mat compare2 = new Mat();
		System.out.print("teste");
		for(Mat mat:cadastrados.keySet()) {
			System.out.print("IOJFIOEWJIOS");
			Imgproc.cvtColor(mat, compare1, Imgproc.COLOR_RGB2RGBA);
			Imgproc.cvtColor(foto, compare2, Imgproc.COLOR_RGB2RGBA);
		}
		
	   };
	public void cadastrar() {
		System.out.println("me chamaram");
		Pessoa p1 = new Pessoa("Cesar", "22/03/2022", "21:48", "C:\\Users\\Teste\\Pictures\\Camera Roll\\WIN_20220322_21_38_20_Pro.jpg");
		cadastrados.put(foto, p1);
		System.out.println(cadastrados.keySet().toString());
	}
	@FXML
	public void testar() {
	}
}
