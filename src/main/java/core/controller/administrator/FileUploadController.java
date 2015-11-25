package core.controller.administrator;

import core.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/upload")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploader;

    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    public String uploadFile (@RequestParam("file-name") String fileName) {
        fileUploader.upload(fileName);
        return "redirect:admin/upload";
    }
}