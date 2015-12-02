package core.controller.administrator;

import core.event.DataCollection;
import core.helper.StringResources;
import core.service.TermManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

    @Autowired
    private DataCollection dataCollection;
    @Autowired
    private TermManagerService termManager;

    @RequestMapping(value = "/admin/upload/confirm")
    public ModelAndView uploadFile (@RequestParam("fileName") String fileName,
                                    @RequestParam("termId") int termId,
                                    ModelAndView model) {
        model.setViewName("upload");
        model.addObject("pageHeading", StringResources.ADMINISTRATOR_UPLOAD);
        model.addObject("terms", termManager.getAllPopulatedTerms());
        if (dataCollection.readFile(fileName, termId)) {
            model.addObject("errorMessage", "Could not upload");
        }else {
            model.addObject("errorMessage", "Upload success");
        }
        return model;
    }
}