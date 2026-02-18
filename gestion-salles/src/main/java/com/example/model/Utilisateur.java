package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    @Column(nullable = false)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Column(unique = true, nullable = false)
    private String email;

    @Min(18)
    private int age;

    @NotBlank
    private String role;

    private boolean actif = true;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Format de téléphone invalide")
    private String telephone;

    // Constructeur par défaut requis par JPA
    public Utilisateur() {
    }

    // Constructeur 3 paramètres (nom, prenom, email)
    public Utilisateur(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = 18;          // valeur par défaut
        this.role = "USER";     // valeur par défaut
    }

    // Constructeur 4 paramètres (nom, prenom, email, age)
    public Utilisateur(String nom, String prenom, String email, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.role = "USER";     // valeur par défaut
    }

    // Constructeur 5 paramètres (nom, prenom, email, age, role)
    public Utilisateur(String nom, String prenom, String email, int age, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", actif=" + actif +
                ", dateNaissance=" + dateNaissance +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
