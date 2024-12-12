# WatPlan

Simple application that converts WAT's (Military University of Technology in Warsaw) schedule from WCY's site into
Google Calendar.

All of us WAT students know what it means to face sudden schedule changes or to search for classes in other groups to
make up for missed ones at a convenient time. Hence the idea for this application! WatPlan allows you to download schedules from
multiple groups and selected subjects and saves each schedule in a separate calendar in your Google Calendar. It was designed
with the Faculty of Cybernetics schedule in mind, so it is certainly compatible with it. I am not sure if it works with
schedules from other faculties.

## Installation Guide

1. **Download the project and open it in Intellij.**
2. **Create an application on your Google account:**
    - Log in to [Google Cloud Console](https://console.cloud.google.com/)
    - Create a new project with any name.
3. **Enable Calendar API:**
    - Navigate to the options menu on the left-hand side → APIs & Services → Library.
    - Search for "Google Calendar API."
    - Enable the Google Calendar API.
4. **Configure the OAuth consent screen:**
    - Navigate to the options menu on the left-hand side → APIs & Services → OAuth consent screen.
    - Create a new consent screen:
        - Application name: WatPlan.
        - User type: External.
        - Enter your email address.
5. **Create an OAuth client ID:**
    - Application type: Desktop app.
    - Name: Any name.
    - Download the JSON file and place it in the `resources` folder.
6. **Add the required scope:**
    - Edit your application in the OAuth consent screen tab.
    - Go to the "Scopes" tab.
    - Add a scope → Search for "Google Calendar API," select the `/auth/calendar` scope → Update.
    - Go to the "Test users" tab.
    - Add a test user by entering your email address.
    - Save the changes.
7. **Configure the project:**
    - Add the `credentials.json` file to the `resources` folder.
    - Modify the `userId` in the `resources/config/userID` file.
    - Add group names in the `resources/config/groups` file using uppercase letters and full names as they appear on the
      schedule website.
    - Add subjects to files named after the groups in the `resources/config/filters/` directory, which were previously
      listed in the `groups` file. Subject names must match exactly as they appear on the website.
8. **Run the main file as you would any program in java in Intellij.**

Changes to the `watURL` file should not be necessary, but if the schedule URL changes, update it in this file.

Unfortunately, the installation process is somewhat cumbersome. I am aware of this – I am still not experienced enough
to create a simpler application, but this may change in the future.

## How It Works

WatPlan fetches the schedules from the URL specified in the `resources/config/watURL` file, processes it into calendar
objects, and integrates them into Google Calendar.

---

# WatPlan

Prosta aplikacja, która konwertuje plan zajęć WAT-u (Wojskowej Akademii Technicznej w Warszawie) ze strony WCY do
Kalendarza Google.

Wszyscy my studenci WATu dobrze wiemy, co to znaczy nagła zmiana planu albo szukanie lekcji z innych grup w celu
odrobienia zajęć w dogodnym terminie. Stąd ta aplikacja! WatPlan umożliwia pobranie planu z wielu grup i wybranych
przedmiotów, i zapisuje każdy plan w osobnym kalendarzu w Google Calendar. Pisany z myślą o planie na wydziale
cybernetyki, a więc z nim jest na pewno kompatybilny. Nie wiem, czy zadziała z planami z innych wydziałów.

## Sposób instalacji

1. **Ściągnięcie projektu i odpalenie w Intellij.**
2. **Stworzenie aplikacji na swoim koncie Google:**
    - Zalogowanie się w [Google Cloud Console](https://console.cloud.google.com/)
    - Utwórz nowy projekt o dowolnej nazwie.
3. **Włącz Calendar API:**
    - Opcje w wysuwanej liście z lewej strony ekranu → Interfejsy API i usługi → Biblioteka.
    - Wyszukaj "Google Calendar API."
    - Włącz Google Calendar API.
4. **Skonfiguruj ekran zgody OAuth:**
    - Opcje w wysuwanej liście z lewej strony ekranu → Interfejsy API i usługi → Ekran zgody OAuth.
    - Utwórz nowy ekran:
        - Nazwa aplikacji: WatPlan.
        - Odbiorcy: z zewnątrz.
        - Wpisz swój adres e-mail.
5. **Utwórz identyfikator klienta OAuth:**
    - Typ aplikacji: aplikacja komputerowa.
    - Nazwa: dowolna.
    - Pobierz JSON i wklej go do folderu `resources`.
6. **Dodanie zakresu (scope):**
    - Edytuj aplikację w zakładce ekran zgody OAuth.
    - Przejdź do zakładki "Zakresy".
    - Dodaj zakres → Wyszukaj "Google Calendar API," zakres `/auth/calendar` → Zaznacz → Zaktualizuj.
    - Przejdź do zakładki "Użytkownicy testowi".
    - Dodaj nowego użytkownika i wpisz swój adres e-mail.
    - Zapisz zmiany.
7. **Konfiguracja projektu:**
    - Dodaj plik `credentials.json` do folderu `resources`.
    - Zmień `userId` w pliku `resources/config/userID`.
    - Dodaj nazwy grup w pliku `resources/config/groups` wielkimi literami, w pełnej nazwie, tak jak jest na stronie
      wydziału cybernetyki.
    - Dodaj przedmioty do plików o nazwach grup w lokalizacji `resources/config/filters/`, które zostały wcześniej
      zapisane w pliku `groups`. Nazwy przedmiotów muszą być pełne, tak jak się wyświetlają na stronie.
8. **Uruchom maina tak jak każdy program w javie w Intellij.**

Zmiana w pliku `watURL` nie powinna być potrzebna, ale jeśli okaże się, że zmieniono adres do planu, należy to zmienić w
tym pliku.

Niestety proces instalacji jest dosyć skomplikowany. Wiem o tym – jestem jeszcze za chudy w uszach, żeby zrobić
prostszą aplikację, ale może się to w przyszłości zmienić.

## Jak to działa

WatPlan pobiera plany ze strony określonej w pliku `resources/config/watURL`, przetwarza go na obiekty kalendarzowe i
wprowadza do kalendarza Google.

---

**Autor:** Michał Kosieradzki  
**Kontakt:** [michal.kosieradzki@post.pl](mailto:michal.kosieradzki@post.pl)

