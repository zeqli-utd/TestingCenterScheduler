package core.controller.administrator;

import core.helper.StringResources;
import core.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/upload")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploader;

    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    public ModelAndView uploadFile (@RequestParam("file-name") String fileName,
                              ModelAndView model) {
        model.setViewName("admin/upload");
        model.addObject("pageHeading", StringResources.ADMINISTRATOR_UPLOAD);
        if (fileUploader.upload(fileName)) {
            model.addObject("errorMessage", "Could not upload");
        }else {
            model.addObject("errorMessage", "Upload success");
        }
        return model;
    }
}