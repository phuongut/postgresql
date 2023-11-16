package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.NguoiDungDAO;
import com.example.demo.models.User;

@Controller
public class controller {

    @Autowired
    NguoiDungDAO nddao;

    // @RequestMapping("/quizls")
    // public String quizls() {
    // return "quizls";
    // }

    // @RequestMapping("/quiz")
    // public String quiz() {
    // return "quiz";
    // }

    // @RequestMapping("/index")
    // public String index(Model model) {
    // java.util.List<MonHoc> monHocList = mhdao.findAll();
    // model.addAttribute("monHocList", monHocList);
    // return "index";
    // }

    // @RequestMapping("/dethi")
    // public String dethi() {
    // return "dethi";
    // }

    // @RequestMapping("/dethichitiet")
    // public String dethichitiet() {
    // return "dethichitiet";
    // }

    // @RequestMapping("/indexAd")
    // public String index1() {
    // return "indexAd";
    // }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/dangxuat")
    public String logout(jakarta.servlet.http.HttpSession session) {
        session.removeAttribute("tenNguoiDung");
        return "login";
    }

    // @RequestMapping("/lienhe")
    // public String lienhe() {
    // return "lienhe";
    // }

    // @RequestMapping("/gioithieu")
    // public String about() {
    // return "gioithieu";
    // }

    // @RequestMapping("/gopy")
    // public String gopy() {
    // return "gopy";
    // }

    @PostMapping("/login")
    public String session(@RequestParam("email") String email,
            @RequestParam("matKhau") String matKhau,
            jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response,
            jakarta.servlet.http.HttpSession session,
            Model model) {

        User user = nddao.findByEmail(email);
        if (user != null && user.getPassword().equals(matKhau)) {
            session.setAttribute("user", user.getName());
            if (user.isAdmin()) {
                return "redirect:/hello";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng");
            return "login";
        }
    }

    @GetMapping("/dangky")
    public String dangky() {
        return "register";
    }

    @PostMapping("/dangky")
    public String processRegistration(@RequestParam String Id, @RequestParam String rematKhau, @RequestParam String sdt,
            @RequestParam String hoTen, @RequestParam String email, @RequestParam String matKhau, Model model) {
        if (nddao.existsByEmail(email)) {
            model.addAttribute("error", "Email này đã tồn tại");
            return "register";
        } else {
            if (nddao.existsById(0)) {
                model.addAttribute("error", "Tên người dùng này đã tồn tại");
                return "register";
            } else {
                if (matKhau.equals(rematKhau)) {
                    User newUser = new User();
                    // String combinedId = "ND" + Id;
                    newUser.setName(hoTen);
                    newUser.setId(0);
                    newUser.setEmail(email);
                    newUser.setPassword(matKhau);
                    newUser.setSdt(0);
                    nddao.save(newUser);
                    return "redirect:/login"; // Chuyển hướng về trang đăng nhập sau khi đăng ký thành công
                } else {
                    model.addAttribute("error", "Mật khẩu không trùng");
                    return "register";
                }
            }
        }
    }

}
