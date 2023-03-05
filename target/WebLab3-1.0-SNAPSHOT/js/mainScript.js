/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function getDestinations(tableId, context){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById(tableId).innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "data?type=destination&context="+context, true);
    xhttp.send();
}

function getHotels(tableId, destinationID, context){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById(tableId).innerHTML = this.responseText;
    }
    };
    xhttp.open("GET", "data?type=hotel&destID=" + destinationID+"&context="+context, true);
    xhttp.send();
    
}

function showTable(context){
    document.getElementById("main").innerHTML = "<table> <thead> <tr> <th>Name</th> <th>Info</th> </tr></thead> <tbody id='table'></tbody> </table>"
    if(context === "browsing"){
        getDestinations("table", "browsing")
    }
    else{
        getDestinations("table", "choosing")
    } 
}

function showForm(hotelID){
        document.getElementById("main").innerHTML = 
            "<form> \n\
                From date:\n\
                <input type='date' id='fromDate'><br>\n\
                To date:\n\
                <input type='date' id='toDate'><br>\n\
                Number of participants:\n\
                <input type='number' id='numOfParticipants'><br>\n\
            </form>\n\
            <input type='button' value='Book' onclick='createBooking("+ hotelID + ")'/>\n\
            <div id='errorMessage'></div>"
}

function createBooking(hotelID){
    var valuesToSend = "function?";
    
    var numOfParticipants = document.getElementById("numOfParticipants").value;
    valuesToSend+="numOfParticipants=" + numOfParticipants;
    
    var dateFrom = document.getElementById("fromDate").value;
    valuesToSend += "&dateFrom=" + dateFrom;    
    
    var dateTo = document.getElementById("toDate").value;
    valuesToSend += "&dateTo=" + dateTo;    
    
    valuesToSend += "&hotelID=" + hotelID;
    
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById("main").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", valuesToSend, true);
    xhttp.send();
    
}

function getLatestReservation(){
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById("main").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "data?type=lastBooking", true);
    xhttp.send();
    
}
