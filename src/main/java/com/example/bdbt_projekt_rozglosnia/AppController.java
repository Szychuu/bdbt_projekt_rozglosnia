package com.example.bdbt_projekt_rozglosnia;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Controller
public class AppController implements WebMvcConfigurer {

    private final SalesDAO dao;

    public AppController(SalesDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/index","/"})
    public String viewHomePage(Model model) {
        List<Sale> listSale = dao.list();
        model.addAttribute("listSale", listSale);
        
        return "index";
    }


    @RequestMapping(value = {"/admin_main"})
    public String viewAdminPage(Model model) {
        List<Sale> listSale = dao.list();
        model.addAttribute("listSale", listSale);
        Sale sale = new Sale();
        model.addAttribute("sale", sale);
        return "admin/admin_main";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("sale") Sale sale) {
        dao.save(sale);
        return "redirect:/admin_main";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("sale") Sale sale) {
        dao.update(sale);
        return "redirect:/admin_main";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        dao.delete(id);
        return "redirect:/admin_main";
    }


    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/perspectives").setViewName("perspectives");
        registry.addViewController("/admin_main").setViewName("admin/admin_main");
        registry.addViewController("/user_main").setViewName("user/user_main");
    }

    @Controller
    public static class DashboardController {
        @RequestMapping("/perspectives")
        public String defaultSuccessUrl (HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/admin_main";
            }
            else if(request.isUserInRole("USER")) {
                return "redirect:/user_main";
            }
            else {
                return "redirect:/index";
            }
        }
    }
}
