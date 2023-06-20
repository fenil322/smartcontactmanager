package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.*;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ContactRepository contactRepository;

    @ModelAttribute
    public void addCommondata(Model m, Principal principal) {
        String username = principal.getName();
        System.out.println(username);

        User user = userRepository.getUserByUserName(username);
        m.addAttribute("user", user);

    }

    // dashboard
    @RequestMapping("/index")
    public String dashBoard(Model model, Principal principal) {

        return "normal/user_dashboard";
    }

    // opern add contact controller
    @GetMapping("/add-contact")
    public String openAddcontactform(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "normal/add_contact";
    }

    @PostMapping("/process-save")
    public String processContact(

            @ModelAttribute("contact") Contact contact,
            @RequestParam("profilePhoto") MultipartFile file,
            Principal principal,
            HttpSession session) {

        try {

            String name = principal.getName();
            System.out.println("CONTACT " + contact);
            User user = this.userRepository.getUserByUserName(name);

            // image uploading
            if (file.isEmpty()) {
                System.out.println("Image is empty");
            } else {
                contact.setImage(file.getOriginalFilename());
                System.out.println("Image uploaded1");
                File saveFile = new ClassPathResource("static/img").getFile();
                System.out.println("Image uploaded2");
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                System.out.println("Image uploaded3");
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image uploaded4 ");

            }

            user.getContacts().add(contact);
            contact.setUser(user);
            this.userRepository.save(user);
            session.setAttribute("message", new Message("your contact is added", "success"));

            return "redirect:add-contact";
        } catch (Exception e) {
            System.out.println("Erorr");
            session.setAttribute("message", new Message("Somthing went wrong!!" + e.getMessage(), "danger"));
            // return "redirect:add-contact";
            return "normal/add_contact";
        }
    }

    @GetMapping("/show-contacts/{page}")
    public String showcontact(Model m,
            @PathVariable("page") Integer page, Principal principal) {

        m.addAttribute("title", "Contacts");
        String name = principal.getName();
        User user = this.userRepository.getUserByUserName(name);

        Pageable pageable = PageRequest.of(page, 5);

        Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

        m.addAttribute("contacts", contacts);
        m.addAttribute("currentpage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());

        return "normal/show_contact";

    }

    @GetMapping("/{cId}/contact")
    public String contactdetail(@PathVariable("cId") Integer cid,
            HttpSession session, Model m, Principal principal) {

        Optional<Contact> contactoptional = this.contactRepository.findById(cid);
        m.addAttribute("title", "Contact Detail");
        Contact contact = contactoptional.get();

        String name = principal.getName();
        User user = userRepository.getUserByUserName(name);

        if (user.getId() == contact.getUser().getId()) {

            m.addAttribute("contact", contact);
            return "normal/contact";
        } else {
            session.setAttribute("message", new Message("You can not see this contact", "danger"));

            return "redirect:/user/show-contacts/0";
        }
    }

    // delete contact
    @GetMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId, Model model, HttpSession session,
            Principal principal) {

        try {

            Optional<Contact> optionalContact = this.contactRepository.findById(cId);

            Contact contact = optionalContact.get();
            // contact.setUser(null);

            if (contact.getImage() != null && !contact.getImage().equals("default.jpg")) {

                File saveFile = new ClassPathResource("static/img").getFile();
                System.out.println("Image delete2");
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + contact.getImage());
                System.out.println("Image delete3");
                Files.delete(path);
                // Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image delete4 ");
            }

            User user = this.userRepository.getUserByUserName(principal.getName());
            user.getContacts().remove(contact);
            this.userRepository.save(user);
            // this.contactRepository.delete(contact);

            session.setAttribute("message", new Message("contact deleted success!!", "success"));
        } catch (Exception e) {

            session.setAttribute("message", new Message("Somthing went wronge   !!", "danger"));
        }

        return "redirect:/user/show-contacts/0";
    }

    // edit contact
    @PostMapping("/edit-contact/{cId}")
    public String updateform(@PathVariable("cId") Integer cId, Model m) {

        m.addAttribute("title", "Edit Contact");

        Contact contact = this.contactRepository.findById(cId).get();
        System.out.println("Edit" + contact);
        m.addAttribute("contact", contact);
        return "normal/update_form";
    }

    // update contact
    @PostMapping("/process-update")
    public String updateForm(@ModelAttribute("contact") Contact contact,
            @RequestParam("profilePhoto") MultipartFile file,
            Principal principal,
            HttpSession session) {

        try {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/img").getFile();
                if (contact.getImage() != null && !contact.getImage().equals("default.jpg")) {

                    System.out.println("Image delete2");
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + contact.getImage());
                    System.out.println("Image delete3");
                    Files.delete(path);
                    // Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image delete4 ");
                }
                contact.setImage(file.getOriginalFilename());
                System.out.println("Image uploaded1");

                System.out.println("Image uploaded2");
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                System.out.println("Image uploaded3");
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image uploaded4 ");

            }
            System.out.println(contact);
            User user = this.userRepository.getUserByUserName(principal.getName());
            contact.setUser(user);
            session.setAttribute("message", new Message("your contact is updated", "success"));
            this.contactRepository.save(contact);
        } catch (Exception e) {
            session.setAttribute("message",
                    new Message("Somthing went wrong!! Contact not Updated!!" + e.getMessage(), "danger"));

        }

        return "redirect:/user/show-contacts/0";
    }


    @GetMapping("/profile")
    public String profileshow(Model m,Principal principal  ){
       
        m.addAttribute("title", "Profile Page");
        
        return "normal/profile";
    }

    @PostMapping("/process-update-user")
    public String updateuser(){
    
        return "normal/profile";
    }
}
