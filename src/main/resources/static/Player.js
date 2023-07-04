function negativeZahlen() {
    // Überprüfe, ob die ID eine negative Zahl ist
    if (parseInt(id) < 0) {
        alert("Die ID darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob das Alter eine negative Zahl ist
    if (parseInt(age) < 0) {
        alert("Das Alter darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob die Team ID eine negative Zahl ist
    if (parseInt(teamId) < 0) {
        alert(
            "die Team ID darf nicht negativ sein. Wähle ein Team 1-5 schaue dir die Tabelle an unten."
        );
        return;
    }
}

let spielerAngezeigt = false;

function spielerAnzeigen() {
    if (spielerAngezeigt) {
        // Spieler werden angezeigt
        const teams = document.getElementById("player-container");
        teams.innerHTML = "";
        spielerAngezeigt = false;
        return;
    }

    fetch("http://localhost:8080/players", {
        method: "GET",
        headers: {Authorization: "Basic " + btoa("admin:adminpassword")},
    })
        .then((response) => response.json())
        .then((data) => {
            const players = data;

            const tableContainer = document.getElementById("player-container");
            tableContainer.innerHTML = "";
            const table = document.createElement("table");
            tableContainer.appendChild(table);
            const headerRow = document.createElement("tr");
            table.appendChild(headerRow);

            const headerColumns = [
                "Spieler ID",
                "Vorname",
                "Nachname",
                "Alter",
                "Team ID",
            ];

            headerColumns.forEach((columnName) => {
                const headerCell = document.createElement("th");

                headerCell.textContent = columnName;

                headerRow.appendChild(headerCell);
            });

            players.forEach((player) => {
                const playerCols = table.insertRow(-1);
                const id = playerCols.insertCell(0);
                const name = playerCols.insertCell(1);
                const nachname = playerCols.insertCell(2);
                const alter = playerCols.insertCell(3);
                const team = playerCols.insertCell(4);

                id.innerHTML = player.playerId;
                name.innerHTML = player.firstname;
                nachname.innerHTML = player.secondname;
                alter.innerHTML = player.age;
                team.innerHTML = player.teamId;
            });

            spielerAngezeigt = true;
        });
}

//Spieler Hinzufügen
function spielerHinzufuegen() {
    const id = document.getElementById("addId").value;
    const firstname = document.getElementById("addFirstname").value;
    const secondname = document.getElementById("addSecondname").value;
    const age = document.getElementById("addAge").value;
    const teamId = document.getElementById("addTeamId").value;

    // Überprüfe, ob die ID eine negative Zahl ist
    if (parseInt(id) < 1) {
        alert("Die ID darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob das Alter eine negative Zahl ist
    if (parseInt(age) < 1) {
        alert("Das Alter darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob die Team ID eine negative Zahl ist
    if (parseInt(teamId) < 1) {
        alert(
            "die Team ID darf nicht negativ sein. Wähle ein Team 1-5 schaue dir die Tabelle an unten."
        );
        return;
    }

    //Überprüfe, ob die Team ID nicht grösser als 5 ist, da es nicht mehr als 5 Teams gibt
    if (parseInt(teamId) > 5) {
        alert(
            "Es gibt nicht mehr als 5 Teams. Wähle ein Team von der untere Tabelle"
        );
        return;
    }

    // Überprüfe, ob ein Feld leer ist
    if (
        id === "" ||
        firstname === "" ||
        secondname === "" ||
        age === "" ||
        teamId === ""
    ) {
        alert("Bitte füllen Sie alle Felder aus.");
        return;
    }

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Basic " + btoa("admin:adminpassword"));

    var raw = JSON.stringify({
        playerId: id,
        firstname: firstname,
        secondname: secondname,
        age: age,
        teamId: teamId,
    });

    var requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
    };

    console.log(raw);

    fetch("http://localhost:8080/players", requestOptions)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Fehler beim Hinzufügen des Spielers.");
            }
            return response.text();
        })
        .then((result) => {
            console.log(result);
            alert("Spieler wurde erfolgreich hinzugefügt.");
        })
        .catch((error) => console.log("error", error));

    // Leere die Eingabefelder
    document.getElementById("addId").value = "";
    document.getElementById("addFirstname").value = "";
    document.getElementById("addSecondname").value = "";
    document.getElementById("addAge").value = "";
    document.getElementById("addTeamId").value = "";
}

let teamAngezeigt = false;

//Team Anzeigen
function teamAnzeigen() {
    if (teamAngezeigt) {
        // Teams werden angezeigt
        const teams = document.getElementById("team-container");
        teams.innerHTML = "";
        teamAngezeigt = false;
        return;
    }

    fetch("http://localhost:8080/teams")
        .then((response) => response.json())
        .then((data) => {
            const teams = data;
            const tableContainer = document.getElementById("team-container");
            tableContainer.innerHTML = "";
            const table = document.createElement("table");
            tableContainer.appendChild(table);
            const headerRow = document.createElement("tr");
            table.appendChild(headerRow);
            const headerColumns = ["Team ID", "Team Name"];
            headerColumns.forEach((columnName) => {
                const headerCell = document.createElement("th");
                headerCell.textContent = columnName;
                headerRow.appendChild(headerCell);
            });

            teams.forEach((team) => {
                const teamCols = table.insertRow(-1);
                const id = teamCols.insertCell(0);
                const name = teamCols.insertCell(1);

                id.innerHTML = team.teamId;
                name.innerHTML = team.teamName;
            });

            teamAngezeigt = true;
        });
}

// Spieler löschen
function spielerLoeschen() {
    var playerId = document.getElementById("deleteId").value;

    // Überprüfe, ob die ID eine negative Zahl ist
    if (parseInt(playerId) < 1) {
        alert("Die ID darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob das Feld leer ist
    if (playerId === "") {
        alert("Bitte geben Sie eine Spieler-ID ein.");
        return; // Beende die Funktion, um das Löschen abzubrechen
    }

    var myHeaders = new Headers();
    myHeaders.append("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA==");

    var raw = JSON.stringify({
        playerId: playerId,
    });

    var requestOptions = {
        method: "DELETE",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
    };


    fetch("http://localhost:8080/players/" + playerId, requestOptions)
               .then((result) => {
            console.log(result);
            if (result === "Player not found") {
                alert("Die Spieler-ID existiert nicht.");
            } else {
                alert("Spieler wurde erfolgreich gelöscht.");
                document.getElementById("deleteId").value = "";

            }
        })
        .catch((error) => console.log("Fehler:", error));

    document.getElementById("deleteId").value = "";
}


//Spieler Updaten
function spielerUpdate() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA==");

    const id = document.getElementById("addId").value;
    const firstname = document.getElementById("addFirstname").value;
    const secondname = document.getElementById("addSecondname").value;
    const age = document.getElementById("addAge").value;
    const teamId = document.getElementById("addTeamId").value;

    // Überprüfe, ob die ID eine negative Zahl ist
    if (parseInt(id) < 1) {
        alert("Die ID darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob das Alter eine negative Zahl ist
    if (parseInt(age) < 1) {
        alert("Das Alter darf nicht negativ sein.");
        return;
    }

    // Überprüfe, ob die Team ID eine negative Zahl ist
    if (parseInt(teamId) < 1) {
        alert(
            "die Team ID darf nicht negativ sein. Wähle ein Team 1-5 schaue dir die Tabelle an unten."
        );
        return;
    }

    //Überprüfe, ob die Team ID nicht grösser als 5 ist, da es nicht mehr als 5 Teams gibt
    if (parseInt(teamId) > 5) {
        alert(
            "Es gibt nicht mehr als 5 Teams. Wähle ein Team von der untere Tabelle"
        );
        return;
    }

    // Überprüfe, ob ein Feld leer ist
    if (
        id === "" ||
        firstname === "" ||
        secondname === "" ||
        age === "" ||
        teamId === ""
    ) {
        alert("Bitte füllen Sie alle Felder aus.");
        return;
    }

    var raw = JSON.stringify({
        playerId: id,
        firstname: firstname,
        secondname: secondname,
        age: age,
        teamId: teamId,
    });

    var requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
    };

    fetch("http://localhost:8080/players", requestOptions)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Fehler beim updaten des Spielers.");
            }
            return response.text();
        })
        .then((result) => {
            console.log(result);
            alert("Spieler wurde erfolgreich updated.");
        })
        .catch((error) => console.log("error", error));

    // Leere die Eingabefelder
    document.getElementById("addId").value = "";
    document.getElementById("addFirstname").value = "";
    document.getElementById("addSecondname").value = "";
    document.getElementById("addAge").value = "";
    document.getElementById("addTeamId").value = "";
}
