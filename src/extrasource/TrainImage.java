package extrasource;

import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.opencv.core.Core;


public class TrainImage 
{
    public static void main() 
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        File root = new File("C:\\Users\\user\\Desktop\\Class Attendance\\img\\");

        FilenameFilter imgFilter = (File dir, String name) -> {
            name = name.toLowerCase();
            return name.endsWith(".jpg") || name.endsWith(".png");
        };

        File[] imageFiles = root.listFiles(imgFilter);
        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.createBuffer();

        int counter = 0;
        for (File image : imageFiles) 
        {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            resize(img, img, new Size(101, 101));
            int label = Integer.parseInt(image.getName().split("\\.")[1]);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
        }
        
        //train
        LBPHFaceRecognizer faceRecognizer = LBPHFaceRecognizer.create(1, 8, 8, 8, 12);
        faceRecognizer.train(images, labels);
        faceRecognizer.save("C:\\Users\\user\\Desktop\\Class Attendance\\img\\classifierLBPH.yml");
        System.out.println("Training Done!!!");
        
//        IntPointer label = new IntPointer(1);
//        DoublePointer confidence = new DoublePointer(1);
//        Mat face = imread("C:\\Class Attendance\\test\\",CV_LOAD_IMAGE_GRAYSCALE);
//        faceRecognizer.predict(face, label, confidence);
//        int prediction = label.get(0);
//        System.out.println("ID : " + prediction);
//        System.out.println(confidence.get(0));
        
        JOptionPane.showMessageDialog(null, "Images Are Trained!!!", "Message : " + "Message Box", JOptionPane.INFORMATION_MESSAGE);
    }
}
