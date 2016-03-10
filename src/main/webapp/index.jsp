<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>BookWebApp</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link href="bgrins-spectrum-98454b5/spectrum.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div>
            <h2>Pick a Task</h2>
            <form method="POST" action="<%= response.encodeURL("AuthorController?taskType=color")%>">
                <div>
                    <label for="showPaletteOnly">Table Color:</label>
                    <input type="text" id="showPaletteOnly" name="showPaletteOnly" value="${table}"/>
                </div>
                <div>
                    <label for="showPaletteOnly1">Text Color:</label>
                    <input type="text" id="showPaletteOnly1" name="showPaletteOnly1" value="${text}"/>
                </div>
                <button type="submit" class="btn btn-primary">View Authors</button>
        </form>
        </div>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="bgrins-spectrum-98454b5/spectrum.js" type="text/javascript"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
        $("#showPaletteOnly").spectrum({
    showPaletteOnly: true,
    showPalette:true,
     showInput: true,
     showInitial: true,
    hideAfterPaletteSelect:true,
    color: 'white',
    change: function(color){
         $("#showPaletteOnly").val(color);
    },
    palette: [
        ['black', 'white',
        'rgb(255, 128, 0);', 'hsv 100 70 50'],
        ['red', 'yellow', 'green', 'blue', 'violet']
    ]
    });
    $("#showPaletteOnly1").spectrum({
    showPaletteOnly: true,
    showPalette:true,
     showInput: true,
     showInitial: true,
    hideAfterPaletteSelect:true,
    color: 'black',
    change: function(color2){
         $("#showPaletteOnly1").val(color2);
    },
    palette: [
        ['black', 'white',
        'rgb(255, 128, 0);', 'hsv 100 70 50'],
        ['red', 'yellow', 'green', 'blue', 'violet']
    ]
    });
</script>
    </body>
</html>