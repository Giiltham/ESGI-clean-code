package fr.esgi.fx.avis.infrastructure.initialisation;

import com.github.javafaker.Faker;
import fr.esgi.fx.avis.infrastructure.entity.*;
import fr.esgi.fx.avis.infrastructure.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
@AllArgsConstructor
@Profile({"DEV", "PROD"})
@Transactional(readOnly = true)
public class AjoutDonneesInitiales {

    private EditeurJpaRepository editeurJpaRepository;
    private ClassificationJpaRepository classificationJpaRepository;
    private GenreJpaRepository genreJpaRepository;
    private PlateformeJpaRepository plateformeJpaRepository;
    private JeuJpaRepository jeuJpaRepository;
    private JoueurJpaRepository joueurJpaRepository;
    private ModerateurJpaRepository moderateurJpaRepository;
    private AvisJpaRepository avisJpaRepository;
    private AvatarJpaRepository avatarJPARepository;
    @PersistenceContext
    private EntityManager entityManager;

    // Le fait de déclarer l'attribut en static va dispenser Spring de gérer l'objet
    private static Faker faker = new Faker(Locale.FRENCH);

   // @Override
   @EventListener(ApplicationReadyEvent.class)
   @Transactional
   // public void run(String... args) throws Exception {
   public void init() {
       ajouterEditeurs();
       ajouterClassifications();
       ajouterGenres();
       ajouterPlateformes();
       ajouterJeux();
       ajouterAvatars();
       ajouterJoueurs(100);
       ajouterModerateur();
       ajouterAvis(200);
       //afficherStatistiques();
    }

    private void ajouterAvatars() {
       avatarJPARepository.save(new AvatarEntity("Avatar 1"));
       avatarJPARepository.save(new AvatarEntity("Avatar 2"));
    }

    private void ajouterAvis(int nbAvisAAjouter) {
        if (avisJpaRepository.count() == 0) {
            Random random = new Random();
            List<JoueurEntity> joueurs = joueurJpaRepository.findAll();
            for (int i = 0; i < nbAvisAAjouter; i++) {
                JoueurEntity joueur = joueurs.get(random.nextInt(joueurs.size()));
                AvisEntity avis = new AvisEntity(faker.letterify("????????"), jeuJpaRepository.findGamesRandomlySorted().get(0), joueur);
                avis.setNote(random.nextFloat(21));
                joueur.getAvis().add(avis);
                avisJpaRepository.save(avis);
            }
        }
    }

    private void ajouterJoueurs(int nbJoueursAAjouter) {
        if (joueurJpaRepository.count() == 0) {
            Random random = new Random();
            Calendar calendar = Calendar.getInstance();
            Map<String, JoueurEntity> map = new HashMap<>();
            int compteur = 0;
            while (compteur<nbJoueursAAjouter) {
                compteur++;
                calendar.set(1940, 1, 1);
                Date dateDebut = calendar.getTime();
                calendar = Calendar.getInstance();
                calendar.set(2003, 1, 1);
                Date dateFin = calendar.getTime();
                Date dateAleatoire = faker.date().between(dateDebut, dateFin);
                calendar.setTime(dateAleatoire);
                LocalDate dateDeNaissance = dateAleatoire.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String prenom = faker.name().firstName();
                String email = prenom + "." + faker.name().lastName().replaceAll(" ", "") + "@soprasteria.com";

                /*
                 * Sans Builder : JoueurEntity joueur = new JoueurEntity(); joueur.setPseudo(prenom +
                 * String.valueOf(random.nextInt(999) + 1000)); joueur.setEmail(email);
                 * joueur.setMotDePasse(String.valueOf(random.nextInt(99999999) + 10000000));
                 * joueur.setDateDeNaissance(dateDeNaissance);
                 */

                JoueurEntity joueur = JoueurEntity.builder().pseudo(prenom + String.valueOf(random.nextInt(999) + 1000))
                        .email(email).motDePasse(String.valueOf(random.nextInt(99999999) + 10000000))
                        .dateDeNaissance(dateDeNaissance).build();

                //map.put(joueur.getEmail(), joueur);
                joueurJpaRepository.save(joueur);
            }
            //joueurRepository.saveAll(map.values());
            joueurJpaRepository.save(JoueurEntity.builder().pseudo("test").motDePasse("anniversaire")
                            .email("test@m2iformation.fr")
                    .dateDeNaissance(LocalDate.of(1999, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth())).build());
        }
    }

    private void ajouterModerateur() {
        moderateurJpaRepository.save(new ModerateurEntity("Peppe", "azerty", "peppe@spiagge.it", "+39123456789"));
    }

    @Transactional(readOnly = true)
    void afficherStatistiques() {

        // méthode qui renvoie les plateformes dont le nom contient ce qui est donné en paramètre
        System.out.println("Méthode qui renvoie les plateformes dont le nom contient ce qui est donné en paramètre");
        plateformeJpaRepository.findByNomContaining("Sta").forEach(System.out::println);

        // méthode qui renvoie les éditeurs n'ayant pas encore édité de jeux
        System.out.println("Méthode qui renvoie les éditeurs n'ayant pas encore édité de jeux");
        editeurJpaRepository.findEditorsWithoutGames().forEach(e -> System.out.println(e.getNom()));

        // R5 : méthode par dérivation qui renvoie les jeux disponibles sur la plateforme donnée en paramètre
        System.out.println("Méthode par dérivation qui renvoie les jeux disponibles sur la plateforme donnée en paramètre");
        jeuJpaRepository.findByPlateformes(plateformeJpaRepository.findByNom("Nintendo Wii")).forEach(p -> System.out.println(p.getNom()));
    }

    private void ajouterJeux() {
       if (jeuJpaRepository.count()==0) {
           EditeurEntity nintendo = editeurJpaRepository.findByNom("Nintendo").orElseThrow();
           EditeurEntity ubisoft = editeurJpaRepository.findByNom("Ubisoft").orElseThrow();
           EditeurEntity riot = editeurJpaRepository.findByNom("Riot Games").orElseThrow();
           EditeurEntity ankama = editeurJpaRepository.findByNom("Ankama").orElseThrow();
           EditeurEntity bioWare = editeurJpaRepository.findByNom("BioWare").orElseThrow();
           EditeurEntity cdProjeckRed = editeurJpaRepository.findByNom("CD Projekt Red").orElseThrow();
           EditeurEntity blizzard = editeurJpaRepository.findByNom("Blizzard").orElseThrow();
           EditeurEntity fromSoftware = editeurJpaRepository.findByNom("FromSoftware").orElseThrow();
           EditeurEntity naughtyDog = editeurJpaRepository.findByNom("Naughty Dog").orElseThrow();
           EditeurEntity hazelightStudios = editeurJpaRepository.findByNom("Hazelight Studios").orElseThrow();
           EditeurEntity idSoftware = editeurJpaRepository.findByNom("idSoftware").orElseThrow();

           GenreEntity moba = genreJpaRepository.findByNom("MOBA (Multiplayer online battle arena)");
           GenreEntity rpg = genreJpaRepository.findByNom("RPG (Role-playing game))");

           JeuEntity jeu = new JeuEntity("Animal Crossing New Horizons", LocalDate.of(2020, 3, 20), nintendo);
           jeu.setPlateformes(Arrays.asList(plateformeJpaRepository.findAll().get(0), plateformeJpaRepository.findAll().get(1)));
           jeuJpaRepository.save(jeu);

           jeuJpaRepository.save(new JeuEntity("Zelda Tears of the Kingdom", LocalDate.of(2023, 5, 12), nintendo));
           jeuJpaRepository.save(new JeuEntity("Assassin's Creed Valhalla", LocalDate.of(2020, 11, 10), ubisoft));

           jeuJpaRepository.save(new JeuEntity("Warframe"));
           jeuJpaRepository.save(new JeuEntity("Final Fantasy VIII"));
           jeuJpaRepository.save(new JeuEntity("Monster Hunter:World"));
           jeuJpaRepository.save(new JeuEntity("Xenoblade Chronicles"));
           jeuJpaRepository.save(new JeuEntity("Nier:Automata"));
           jeuJpaRepository.save(new JeuEntity("Lost Ark"));
           jeuJpaRepository.save(new JeuEntity("Aion"));
           jeuJpaRepository.save(new JeuEntity("Métin 2"));
           jeuJpaRepository.save(new JeuEntity("Tera"));
           jeuJpaRepository.save(new JeuEntity("Tunic"));
           jeuJpaRepository.save(new JeuEntity("Satisfactory"));
           jeuJpaRepository.save(new JeuEntity("Valorant"));
           jeuJpaRepository.save(new JeuEntity("Octopath Travellers"));
           jeuJpaRepository.save(new JeuEntity("Minecraft"));
           jeuJpaRepository.save(new JeuEntity("Outer Wild"));
           jeuJpaRepository.save(new JeuEntity("Strays"));
           jeuJpaRepository.save(new JeuEntity("Nier:Replicant"));

           jeuJpaRepository.save(new JeuEntity("The last of us part II", editeurJpaRepository.findByNom("Naughty Dog").orElseThrow()));
           jeuJpaRepository.save(new JeuEntity("GTA V", editeurJpaRepository.findByNom("Rockstar").orElseThrow()));
           jeuJpaRepository.save(new JeuEntity("Splinter cell", editeurJpaRepository.findByNom("Ubisoft").orElseThrow()));

           jeuJpaRepository.save(new JeuEntity("Mario Kart 8 ", "jeu de course", LocalDate.of(2014, 5, 29),
                   editeurJpaRepository.findByNom("Nintendo").orElseThrow()));
           jeuJpaRepository.save(new JeuEntity("FIFA 2022", "jeu de simulation de football", LocalDate.of(2021, 9, 27),
                   editeurJpaRepository.findByNom("Electronic Arts").orElseThrow()));

           jeuJpaRepository.save(new JeuEntity("League Of Legends", LocalDate.of(2009, 10, 27), riot, moba));
           jeuJpaRepository.save(new JeuEntity("Dofus", LocalDate.of(2004, 9, 1), ankama, rpg));

           jeuJpaRepository.save(new JeuEntity("Call of Duty", editeurJpaRepository.findByNom("Activision").orElseThrow()));
           jeuJpaRepository.save(new JeuEntity("EVE", editeurJpaRepository.findByNom("CCP").orElseThrow()));
           jeuJpaRepository.save(new JeuEntity("The Elder Scrolls : Skyrim", editeurJpaRepository.findByNom("Bethesda").orElseThrow()));

           jeuJpaRepository.save(new JeuEntity("Dragon Age: Inquisition", LocalDate.of(2014, 11, 21), bioWare));
           jeuJpaRepository.save(new JeuEntity("The Witcher 3: Wild Hunt", LocalDate.of(2015, 5, 24), cdProjeckRed));
           jeuJpaRepository.save(new JeuEntity("Overwatch", LocalDate.of(2016, 11, 21), blizzard));
           jeuJpaRepository.save(new JeuEntity("The Legend of Zelda: Breath of the Wild", LocalDate.of(2017, 3, 3), nintendo));
           jeuJpaRepository.save(new JeuEntity("God of War", LocalDate.of(2018, 4, 4), ubisoft));
           jeuJpaRepository.save(new JeuEntity("Sekiro: Shadows Die Twice", LocalDate.of(2019, 3, 22), fromSoftware));
           jeuJpaRepository.save(new JeuEntity("The Last of Us Part II", LocalDate.of(2020, 6, 19), naughtyDog));
           jeuJpaRepository.save(new JeuEntity("It Takes Two", LocalDate.of(2021, 11, 4), hazelightStudios));
           jeuJpaRepository.save(new JeuEntity("Elden Ring", LocalDate.of(2022, 2, 25), fromSoftware));

           jeuJpaRepository.save(new JeuEntity("Doom eternal", LocalDate.of(2020, 3, 20), idSoftware));

           jeuJpaRepository.save(new JeuEntity("Palworld", LocalDate.of(2024, 1, 19), idSoftware));

           jeuJpaRepository.save(new JeuEntity("Pikmin", LocalDate.of(2001, 10, 26), editeurJpaRepository.findByNom("Nintendo").orElseThrow()));

           jeuJpaRepository.save(new JeuEntity("Halo 5", LocalDate.of(2015, 10, 27), editeurJpaRepository.findByNom("Microsoft").orElseThrow()));
       }
    }

    private void ajouterPlateformes() {
        if (plateformeJpaRepository.count() == 0) {
            plateformeJpaRepository.save(new PlateformeEntity("Amstrad CPC"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo Wii"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo Wii U"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo Switch"));
            plateformeJpaRepository.save(new PlateformeEntity("Windows"));
            plateformeJpaRepository.save(new PlateformeEntity("MacOS"));
            plateformeJpaRepository.save(new PlateformeEntity("Steam"));
            plateformeJpaRepository.save(new PlateformeEntity("Neo-Geo"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation 1"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation 2"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation 3"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation 4"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation 5"));
            plateformeJpaRepository.save(new PlateformeEntity("PlayStation Vita"));
            plateformeJpaRepository.save(new PlateformeEntity("PSP"));
            plateformeJpaRepository.save(new PlateformeEntity("Sega Dreamcast"));
            plateformeJpaRepository.save(new PlateformeEntity("Sega Mastersystem"));
            plateformeJpaRepository.save(new PlateformeEntity("Sega Saturn"));
            plateformeJpaRepository.save(new PlateformeEntity("Xbox One"));
            plateformeJpaRepository.save(new PlateformeEntity("Xbox One Series"));
            plateformeJpaRepository.save(new PlateformeEntity("Xbox 360"));
            plateformeJpaRepository.save(new PlateformeEntity("Amiga"));
            plateformeJpaRepository.save(new PlateformeEntity("Android"));
            plateformeJpaRepository.save(new PlateformeEntity("Atari 8-bit"));
            plateformeJpaRepository.save(new PlateformeEntity("Atari Jaguar"));
            plateformeJpaRepository.save(new PlateformeEntity("Commodore 64"));
            plateformeJpaRepository.save(new PlateformeEntity("Game Boy"));
            plateformeJpaRepository.save(new PlateformeEntity("Game Boy Color"));
            plateformeJpaRepository.save(new PlateformeEntity("Game Boy Advance"));
            plateformeJpaRepository.save(new PlateformeEntity("Game Boy Advance SP"));
            plateformeJpaRepository.save(new PlateformeEntity("NES"));
            plateformeJpaRepository.save(new PlateformeEntity("PC-Engine"));
            plateformeJpaRepository.save(new PlateformeEntity("SNES"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo 3DS"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo 64"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo DS"));
            plateformeJpaRepository.save(new PlateformeEntity("Nintendo Gamecube"));
        }
    }

    private void ajouterGenres() {
        if (genreJpaRepository.count() == 0) {
            genreJpaRepository.save(new GenreEntity("FPS (First person shooter)"));
            genreJpaRepository.save(new GenreEntity("TS (real-time strategy)"));
            genreJpaRepository.save(new GenreEntity("RPG (Role-playing game))"));
            genreJpaRepository.save(new GenreEntity("Simulation"));
            genreJpaRepository.save(new GenreEntity("Gestion"));
            genreJpaRepository.save(new GenreEntity("TPS (Third person shooter)"));
            genreJpaRepository.save(new GenreEntity("Digital collectible card game"));
            genreJpaRepository.save(new GenreEntity("MOBA (Multiplayer online battle arena"));
            genreJpaRepository.save(new GenreEntity("Hack n Slash"));
            genreJpaRepository.save(new GenreEntity("Action/Aventure"));
            genreJpaRepository.save(new GenreEntity("Point and click"));
            genreJpaRepository.save(new GenreEntity("Plates-formes"));
            genreJpaRepository.save(new GenreEntity("4X (eXplore, eXpand, eXploit and eXterminate)"));
            genreJpaRepository.save(new GenreEntity("Tactical RPG"));
            genreJpaRepository.save(new GenreEntity("Action RPG"));
        }
    }

    @Transactional(readOnly = true)
    void ajouterClassifications() {
        if (classificationJpaRepository.count() == 0) {
            classificationJpaRepository.save(new ClassificationEntity("PG 3", "0000FF"));
            classificationJpaRepository.save(new ClassificationEntity("PG 7", "00FF00"));
            classificationJpaRepository.save(new ClassificationEntity("PG 12", "FF0000"));
            classificationJpaRepository.save(new ClassificationEntity("PG 16", "FFFF00"));
            classificationJpaRepository.save(new ClassificationEntity("PG 18","00FFFF"));
            classificationJpaRepository.save(new ClassificationEntity("Aucune", "FFFFFF"));
        }
    }


    private void ajouterEditeurs() {
        if (editeurJpaRepository.count() == 0) {
            editeurJpaRepository.save(new EditeurEntity("Activision"));
            editeurJpaRepository.save(new EditeurEntity("Amazon Games"));
            editeurJpaRepository.save(new EditeurEntity("Ankama"));
            editeurJpaRepository.save(new EditeurEntity("Bandai Namco"));
            editeurJpaRepository.save(new EditeurEntity("Bethesda"));
            editeurJpaRepository.save(new EditeurEntity("BioWare"));
            editeurJpaRepository.save(new EditeurEntity("Blizzard"));
            editeurJpaRepository.save(new EditeurEntity("Capcom"));
            editeurJpaRepository.save(new EditeurEntity("CCP"));
            editeurJpaRepository.save(new EditeurEntity("CD Projekt Red"));
            editeurJpaRepository.save(new EditeurEntity("Davilex"));
            editeurJpaRepository.save(new EditeurEntity("Digital Extreme"));
            editeurJpaRepository.save(new EditeurEntity("Electronic Arts"));
            editeurJpaRepository.save(new EditeurEntity("Epic Games"));
            editeurJpaRepository.save(new EditeurEntity("FromSoftware"));
            editeurJpaRepository.save(new EditeurEntity("Hazelight Studios"));
            editeurJpaRepository.save(new EditeurEntity("idSoftware"));
            editeurJpaRepository.save(new EditeurEntity("Microsoft"));
            editeurJpaRepository.save(new EditeurEntity("MonolithSoftware"));
            editeurJpaRepository.save(new EditeurEntity("Naughty Dog"));
            editeurJpaRepository.save(new EditeurEntity("Nintendo"));
            editeurJpaRepository.save(new EditeurEntity("Riot Games"));
            editeurJpaRepository.save(new EditeurEntity("Rockstar"));
            editeurJpaRepository.save(new EditeurEntity("Sega"));
            editeurJpaRepository.save(new EditeurEntity("Square Enix"));
            editeurJpaRepository.save(new EditeurEntity("Tencent"));
            editeurJpaRepository.save(new EditeurEntity("Ubisoft"));
            editeurJpaRepository.save(new EditeurEntity("Ultra Software"));
            editeurJpaRepository.save(new EditeurEntity("Valve"));
            editeurJpaRepository.save(new EditeurEntity("Wildcard"));
        }
    }

}
