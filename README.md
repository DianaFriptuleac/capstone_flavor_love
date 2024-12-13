# **Capstone Flavor Love - Backend**

Benvenuti nel progetto **Capstone Flavor Love - Backend**, una piattaforma per gli amanti della cucina che consente agli utenti di registrarsi, esplorare ricette, creare ricettari personalizzati, aggiungere ricette ai preferiti, gestire la lista della spesa e molto altro. Questo backend è implementato con **Spring Boot** e utilizza un database relazionale per gestire le entità.

---

## **Repository del Frontend**

La repository del frontend dell'applicazione è disponibile qui:  
[Flavor Love Frontend](https://github.com/DianaFriptuleac/flavor_love.git)

---

## **Tecnologie Utilizzate**

- **Spring Boot**: Framework per lo sviluppo di applicazioni backend.
- **Spring Security**: Gestione dell'autenticazione e autorizzazione.
- **Hibernate (JPA)**: Gestione della persistenza dei dati.
- **PostgreSQL**: Database relazionale utilizzato per memorizzare i dati.
- **Lombok**: Generazione automatica di boilerplate code.
- **Maven**: Gestione delle dipendenze.

---

## **Funzionalità**

### **Autenticazione**
- Registrazione di nuovi utenti.
- Login con generazione di token JWT.
- Gestione del profilo utente, inclusa la modifica dei dati personali e dell’avatar.

### **Ricette**
- Creazione, modifica e cancellazione di ricette.
- Aggiunta di immagini e ingredienti alle ricette.
- Ricerca di ricette per titolo, categoria e ID.
- Visualizzazione delle ricette salvate.

### **Categorie di Ricette**
- Creazione e gestione delle categorie (solo per admin).
- Ricerca e filtro per categorie.

### **Ricettari**
- Creazione di ricettari personalizzati.
- Aggiunta e rimozione di ricette nei ricettari.

### **Lista della Spesa**
- Aggiunta degli ingredienti delle ricette alla lista spesa.
- Modifica delle quantità o eliminazione degli ingredienti.
- Recupero e svuotamento della lista spesa.

### **Preferiti**
- Aggiunta e gestione delle ricette preferite.

### **Ricette Esterne**
- Recupero di ricette da API esterne (Spoonacular).
- Salvataggio delle ricette esterne nel database.

---

## **Relazioni tra Entità**

### **Schema delle Relazioni**
1. **Utente**
   - **One-to-Many** con **Ricetta**: Un utente può creare più ricette.
   - **One-to-Many** con **ListaSpesa**: Un utente può avere più liste della spesa.
   - **One-to-Many** con **Ricettario**: Un utente può avere più ricettari.

2. **Ricetta**
   - **Many-to-One** con **Utente**: Ogni ricetta è creata da un solo utente.
   - **One-to-Many** con **Ingrediente**: Una ricetta può avere più ingredienti.
   - **Many-to-Many** con **CategoriaRicetta**: Una ricetta può appartenere a più categorie.
   - **One-to-Many** con **ImgRicetta**: Una ricetta può avere più immagini.
   - **Many-to-Many** con **Ricettario**: Una ricetta può essere aggiunta in più ricettari.

3. **CategoriaRicetta**
   - **Many-to-Many** con **Ricetta**: Una categoria può includere più ricette e viceversa.

4. **ListaSpesa**
   - **One-to-Many** con **ListaSpesaElement**: Una lista della spesa contiene più elementi.
   - **Many-to-One** con **Utente**: Ogni lista è associata a un utente.

5. **Ricettario**
   - **Many-to-Many** con **Ricetta**: Un ricettario può includere più ricette.
   - **Many-to-One** con **Utente**: Ogni ricettario è associato a un utente.

6. **Ricette Esterne**
   - **One-to-Many** con **IngredientiRicettaEsterna**: Una ricetta esterna può avere più ingredienti.

---

## **Istruzioni per l’Installazione**

### **Prerequisiti**
- **Java 17+**
- **PostgreSQL** installato e configurato
- **Maven** per la gestione delle dipendenze.
