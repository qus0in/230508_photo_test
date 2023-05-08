package io.playdata.photo.controller;

import io.playdata.photo.model.Photo;
import io.playdata.photo.model.PhotoForm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.playdata.photo.service.PhotoService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class PhotoController {

    @Autowired
    private final PhotoService photoService;

    // 모든 사진 정보를 조회하는 메소드
    @GetMapping("/")
    public String index(Model model) {
        List<Photo> photos = photoService.getAllPhotos();
        model.addAttribute("photos", photos);
        return "index";
    }

    // 사진 추가 폼을 불러오는 메소드
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("photoForm", new PhotoForm());
        return "add";
    }

    // 사진을 추가하는 메소드
    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute("photoForm") @Validated PhotoForm photoForm, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) { // 검증 결과에 오류가 있는 경우
            mav.setViewName("add"); // add.html 뷰를 다시 렌더링
            return mav;
        }
        photoService.addPhoto(photoForm);
        mav.setViewName("redirect:/"); // 메인 페이지로 리다이렉트
        mav.addObject("message", "Photo has been added successfully."); // 추가 성공 메시지 전달
        return mav;
    }

    // 사진 수정 폼을 불러오는 메소드
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PhotoForm photoForm = photoService.getPhotoFormById(id);
        model.addAttribute("photoForm", photoForm);
        return "edit";
    }

    // 사진을 수정하는 메소드
    @PostMapping("/edit")
    public ModelAndView edit(@ModelAttribute("photoForm") @Validated PhotoForm photoForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) { // 검증 결과에 오류가 있는 경우
            mav.setViewName("edit"); // edit.html 뷰를 다시 렌더링
            return mav;
        }
        photoService.updatePhoto(photoForm);
        mav.setViewName("redirect:/"); // 메인 페이지로 리다이렉트
        redirectAttributes.addFlashAttribute("message", "Photo has been updated successfully."); // 수정 성공 메시지 전달
        return mav;
    }

    // 사진을 삭제하는 메소드
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView();
        photoService.deletePhotoById(id);
        mav.setViewName("redirect:/"); // 메인 페이지로 리다이렉트
        mav.addObject("message", "Photo has been deleted successfully."); // 삭제 성공 메시지 전달
        return mav;
    }

}