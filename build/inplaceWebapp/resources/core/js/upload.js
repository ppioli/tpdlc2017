(function() {

var files;
// Add events
$('input[type=file]').on('change', prepareUpload);
$('form').on('submit', uploadFiles);

function prepareUpload(event)
{
    files = event.target.files;
    makeTable(files);
}

function XhrManager(method, url){
    this.method = method;
    this.url = url;
    this.queue = [];

    this.add = function(data)
    {
        this.queue.push(data)
    }

    this.list = function(){
        this.queue.forEach(function(item){
            console.log(item)
        })
    }

    this.run = function(count){
        var _this = this;
        var _thisCount = count;
        if(count<this.queue.length){
            var data = this.queue[count];
            var xhr = new XMLHttpRequest();
            xhr.open(this.method, this.url, true);
            xhr.onload = function () {
              if (xhr.status === 200) {

                var result = JSON.parse(xhr.response);

                if(result.done){
                    $("#row"+count).addClass("active");
                    $("#row"+count+"status").html("Listo");
                } else {
                    $("#row"+count).addClass("danger");
                    $("#row"+count+"status").html(result.message);
                }

              } else {
                console.log("error " + count);
              }
              _this.run(count+1);
            };
            xhr.send(data);
        }
    }
}

function uploadFiles(event)
{
    event.stopPropagation(); // Stop stuff happening
    event.preventDefault(); // Totally stop stuff happening
    var manager = new XhrManager('POST', '/tpdlc2017/upload');
    for (var i = 0; i < files.length; i++) {
        var file = files[i];

        if(!file.type.match("text*")) continue;
        console.log("file type: " + file.type);

        var formData = new FormData();
        formData.append('file', file, file.name);
        manager.add(formData);

    }
    manager.run(0);
}
function makeTable(files) {
    var html = '';
    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        html += '<tr id="row'+i+'">';
        html += '<td>' + i + '</td>';
        html += '<td>' + file.name + '</td>';
        html += '<td id="row'+i+'status">Pendiente</td>';
        html += "</tr>";
    }
    $(html).appendTo('#filesTable');
}
})();
