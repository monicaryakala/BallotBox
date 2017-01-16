package BallotBoxservlets;
 
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

import sun.misc.BASE64Decoder;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;
import static org.opencv.core.Rect.*;

import java.util.Vector;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
/**
 * Servlet implementation class TestServlet
 */



	
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Go to doPost");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**************************** Testing OpenCV-2.4.12 on Windows by Navita*************************************/
			System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
			Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
			System.out.println( "mat = " + mat.dump() );
		/**************************** End of testing OpenCV-2.4.12 on Windows *************************************/
		
		/**************************** Implementation of OpenCV on linux by Kevin *************************************/
		/*System.out.println("Reached in here!!");
		System.out.println(System.getProperty("java.library.path"));
		PrintWriter out = response.getWriter();
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String completeImageData = request.getParameter("mydata");
		String imageDataBytes = completeImageData.substring(completeImageData.indexOf(",")+1);
		
		BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_3BYTE_BGR);
		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		
		imageByte = decoder.decodeBuffer(imageDataBytes);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();

		// write the image to a file
		//File outputfile = new File("/home/insight-lab/JavaWorkSpace/workspace/webcam/face/face.png");
		//System.out.print(image);
		//ImageIO.write(image, "png", outputfile);
		
		// detect face
		CascadeClassifier faceDetector = 
				  new CascadeClassifier("C:/opencv2412/build/share/OpenCV/haarcascades/haarcascade_frontalface_alt.xml");
		  //Mat imageFace = imread("/home/insight-lab/JavaWorkSpace/workspace/webcam/face/face.png");
		Mat imageFace = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		//System.out.println(imageFace);
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		imageFace.put(0, 0, data);  
		//System.out.println(data);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(imageFace, faceDetections);
		for (Rect rect : faceDetections.toArray()) {
		        Core.rectangle(imageFace, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}
		// Save the visualized detection.
		String filename = "E:/Navita/Ualbany/Spring2016/SoftwareEngineering/detectedimage.jpg";
		imwrite(filename, imageFace);	
		
		//convert mat to bufferimage
		imageFace.get(0, 0, data);
		image.getRaster().setDataElements(0, 0, 320, 240, data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		byte[] imageInByteArray = baos.toByteArray();
		String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
		String imageString = "data:image/png;base64," + b64;
		*/
		
		/**************************** Implementation of OpenCV on linux by Kevin *************************************/
		/*****Commented Below out for now*****/
       // String html = "<html><body><img src='" + imageString + "'></body></html>";
        //out.println(html);
		/*******END comment out*********/
		/*****Below forwards to VotingModule.js*******/
		 RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		 dispatcher.forward(request, response);
		
        
	}

}
