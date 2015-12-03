package core.controller.administrator;

import core.event.DataCollection;
import core.helper.StringResources;
import core.service.TermManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class FileUploadController {

    @Autowired
    private DataCollection dataCollection;
    @Autowired
    private TermManagerService termManager;

    @RequestMapping(value = "/admin/upload/confirm/", method = RequestMethod.POST)
    public ModelAndView uploadFile (@RequestParam("file") MultipartFile file,
                                    @RequestParam("termId") int termId,
                                    ModelAndView model) {
        model.setViewName("upload");
        model.addObject("pageHeading", StringResources.ADMINISTRATOR_UPLOAD);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        boolean isUpLoadSuccess;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                isUpLoadSuccess = dataCollection.readFile(serverFile.getAbsolutePath(), termId);
            } catch (Exception e) {
                isUpLoadSuccess = false;
            }
        } else {
            isUpLoadSuccess = false;
        }
        if(isUpLoadSuccess){
            model.addObject("errorMessage", "Upload success");

        }else {
            model.addObject("errorMessage", "Could not upload");
        }
        return model;




    }
}