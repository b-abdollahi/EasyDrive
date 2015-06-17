package Utils;

import play.api.mvc.Result;
import play.mvc.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Behzad on 1/4/2015.
 */
public class Utilities extends Controller {

    private static int random = 0;

    public static int randomNum(){
        if (random == 0){
            random = 1;
            return random;
        } else {
            random = 0;
            return random;
        }
    }


    public static byte[] scale(byte[] fileData, int width) {
        final int DEFAULT_IMAGE_WIDTH = 600;
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            int height = (width * img.getHeight())/ img.getWidth();
            if(width == 0) {
                width = DEFAULT_IMAGE_WIDTH;
            }
            if (img.getWidth()<= width){
                return fileData;
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.createGraphics().drawImage(scaledImage, 0, 0,null);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            byte[] output =  buffer.toByteArray();
            buffer.flush();
            buffer.close();
            in.close();
            return output;
        } catch (IOException e) {
            return null;
        }
    }

//
//    public static Result search (String keyword) {
//        keyword = keyword.trim();
//        if (keyword.equals("")){
//            flash("error", "Empty search term...");
//            return redirect(request().getHeader("referer"));
//        }
//        if (keyword.length() < 3){
//            flash("error", "Search term too short");
//            return redirect(request().getHeader("referer"));
//        }
//        return ok(searchResults.render(Device.searchDevices(keyword),
//                DeviceModel.searchDeviceModels(keyword),
//                DeviceType.searchDeviceTypes(keyword),
//                Manufacturer.searchManufacturers(keyword),
//                Setup.searchSetups(keyword),
//                keyword));
//    }
//
//    public static Result about() {
//        return ok(about.render());
//    }
}
