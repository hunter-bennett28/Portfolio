const IS_FINISHED = 4; //return codes from JSON request to check for
const IS_OK = 200;

var responseObject, currentGame;
var nameAlphas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ'- ";
//Added '-' to list becuase some names are hiphenated
//Added ''' because some names contain it
var alphasPlusAmp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ& ";
//Added '&' to account for AT&T Park
var filterSet = "";
var isIE11 = ((window.navigator.userAgent).indexOf("Trident") !== -1);

function start(){
    
    document.getElementById("year").options.length = 0;
    document.getElementById("month").options.length = 0;
    document.getElementById("day").options.length = 0;
    
    //disabling buttons while no data is retrieved
    document.getElementById("previousGame").disabled = true;
    document.getElementById("nextGame").disabled = true;
    
    //Creating options for selects
    //Populating year
    for(var i = 2015; i <= 2017; i++)
    {
        document.getElementById("year").options[i-2015] = new Option(i, i);
    }
    
    //Populating month
    for(var i = 1; i <= 12; i++)
    {
        if(i < 10)
        {
            document.getElementById("month").options[i-1] = new Option(i, "0" + i);
        }//Adding 0 before single digits to accurately reflect site's format
        else
        {
            document.getElementById("month").options[i-1] = new Option(i, i);
        }
    }
    
    //Populating day
    for(var i = 1; i <= 31; i++)
    {
        if(i < 10)
        {
            document.getElementById("day").options[i-1] = new Option(i, "0" + i);
        }//Adding 0 before single digits to accurately reflect site's format
        else
        {
            document.getElementById("day").options[i-1] = new Option(i, i);
        }
    }
}

function filterText(ref) 
{
    //enabling the '&' symbol in the venue text box only
    if (ref.id === "venue") 
    {
        filterSet = alphasPlusAmp;
    }
    else
    {
        filterSet = nameAlphas;
    }

    //Using correct function based on browser
    if (isIE11) 
    {
        if (!nCharOK(window.event.keyCode)) 
        {
            window.event.preventDefault();
        }//only allowing browser to insert valid keys
    }
    else 
    {
        if (!nCharOK(window.event.keyCode)) 
        {
            window.event.returnValue = null;
        }
    }
}

function nCharOK(c) 
{
    var ch = (String.fromCharCode(c));
    ch = ch.toUpperCase();

    //checking if key is contained in our filterSet
    if (filterSet.indexOf(ch) !== -1) 
    {
        return true;
    }
    else 
    {
        return false;
    }
}

function retrieveData(url)
{
    var request = new XMLHttpRequest();
    
    //Creating event handler for completion of successful data retrieval
    request.onreadystatechange = function()
    {
        if(request.readyState === IS_FINISHED && request.status === IS_OK)
            {
                responseObject = JSON.parse(request.responseText);
                if(responseObject === undefined)
                {
                    alert("No games on this day");        
                }
                else
                {
                    currentGame = 0;
                    outputData(currentGame); //starting at first game of given day
                }
            }
    }
    request.open('GET', url);
    request.send();
}

function outputData(game)
{   
    try
    {
        document.getElementById("homeTeam").value = 
            responseObject.data.games.game[game].home_team_name;
        document.getElementById("awayTeam").value = 
            responseObject.data.games.game[game].away_team_name;
        document.getElementById("winningPitcher").value = 
            responseObject.data.games.game[game].winning_pitcher.first + " " 
            + responseObject.data.games.game[game].winning_pitcher.last;
        document.getElementById("losingPitcher").value = 
            responseObject.data.games.game[game].losing_pitcher.first
            + " " + responseObject.data.games.game[game].losing_pitcher.last;
        document.getElementById("venue").value = 
            responseObject.data.games.game[game].venue;
        
        //enabling correct buttons based on index
        if(game === 0)
        {
            document.getElementById("nextGame").disabled = false;
            document.getElementById("previousGame").disabled = true;
        }
        else if(game === responseObject.data.games.game.length - 1)
        {
            document.getElementById("nextGame").disabled = true;
        }
        else
        {
            document.getElementById("nextGame").disabled = false;
            document.getElementById("previousGame").disabled = false;
        }
    }
    catch (error) //checking for days without games
    {
        alert("No games on this day.");
        
        //clearing out fields
        document.getElementById("homeTeam").value = "";
        document.getElementById("awayTeam").value = "";
        document.getElementById("winningPitcher").value = "";
        document.getElementById("losingPitcher").value = "";
        document.getElementById("venue").value = "";
        
        //disabling buttons
        document.getElementById("nextGame").disabled = true;
        document.getElementById("previousGame").disabled = true;
    }
}

function getURL()
{
    //Populating URL with chosen date
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;
    var day = document.getElementById("day").value;
    
    var url = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + day + "/master_scoreboard.json";
    
    retrieveData(url);
}

function retrievePrevious()
{
    outputData(--currentGame);
}

function retrieveNext()
{
    outputData(++currentGame);   
}