package com.example.bdbt_projekt_rozglosnia;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AppController implements WebMvcConfigurer {

    private final WykonawcaDAO dao;

    public AppController(WykonawcaDAO dao) {
        this.dao = dao;
    }


    @RequestMapping(value = {"/index", "/"})
    public String viewHomePage(Model model) {
        List<Wykonawca> listWykonawca = dao.list();
        model.addAttribute("listWykonawca", listWykonawca);
        return "index";
    }


    @RequestMapping(value = {"/admin_main"})
    public String viewAdminPage(Model model) {
        List<Wykonawca> listWykonawca = dao.list();
        model.addAttribute("listWykonawca", listWykonawca);

        Wykonawca wykonawca = new Wykonawca();
        model.addAttribute("wykonawca", wykonawca);

        return "admin/admin_main";
    }

    @Autowired
    private SluchaczDAO sluchaczDao;

    @RequestMapping(value = {"/user_main"})
    public String viewUserPage(Model model, HttpServletRequest request) {
        String currentUsername = request.getUserPrincipal().getName();

        Sluchacz profil = sluchaczDao.getByEmail(currentUsername);
        model.addAttribute("profil", profil);

        List<Wykonawca> listWykonawca = dao.list();
        model.addAttribute("listWykonawca", listWykonawca);

        return "user/user_main";
    }

    @RequestMapping(value = "/update_profile", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute("profil") Sluchacz sluchacz, RedirectAttributes redirectAttributes) {

        if (sluchacz.getNr_telefonu() != null && sluchacz.getNr_telefonu().matches("\\d{9}")) {
            sluchaczDao.updateProfile(sluchacz);
            redirectAttributes.addFlashAttribute("successMsg", "Profil został pomyślnie zaktualizowany!");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Błąd: Numer telefonu musi składać się z dokładnie 9 cyfr!");
        }

        return "redirect:/user_main";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("wykonawca") Wykonawca wykonawca, RedirectAttributes redirectAttributes) {
        if (wykonawca.getImie() == null || wykonawca.getImie().trim().isEmpty() ||
                wykonawca.getNazwisko() == null || wykonawca.getNazwisko().trim().isEmpty()) {

            redirectAttributes.addFlashAttribute("errorMsg", "Błąd: Imię i Nazwisko są wymagane!");
            return "redirect:/admin_main";
        }

        try {
            dao.save(wykonawca);
            redirectAttributes.addFlashAttribute("successMsg", "Nowy wykonawca został dodany pomyślnie!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Błąd bazy danych: " + e.getMessage());
        }

        return "redirect:/admin_main";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("wykonawca") Wykonawca wykonawca, RedirectAttributes redirectAttributes) {
        try {
            dao.update(wykonawca);
            redirectAttributes.addFlashAttribute("successMsg", "Zmiany w danych wykonawcy (ID: " + wykonawca.getNr_wykonawcy() + ") zostały pomyślnie zapisane!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Wystąpił błąd podczas aktualizacji danych: " + e.getMessage());
        }
        return "redirect:/admin_main";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            dao.delete(id);
            redirectAttributes.addFlashAttribute("successMsg", "Wykonawca został pomyślnie usunięty z bazy danych!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Błąd podczas usuwania: " + e.getMessage());
        }
        return "redirect:/admin_main";
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/perspectives").setViewName("perspectives");
    }

    @Controller
    public static class DashboardController {
        @RequestMapping("/perspectives")
        public String defaultSuccessUrl(HttpServletRequest request) {
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/admin_main";
            } else if (request.isUserInRole("USER")) {
                return "redirect:/user_main";
            } else {
                return "redirect:/index";
            }
        }
    }
}