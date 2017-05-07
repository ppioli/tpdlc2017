
/*
var form = document.getElementById('file-form');
var fileSelect = document.getElementById('file-select');
var uploadButton = document.getElementById('upload-button');

form.onsubmit = function(event) {
  event.preventDefault();
  uploadButton.innerHTML = 'Uploading...';

  var files = fileSelect.files;
  for (var i = 0; i < files.length; i++) {
        var file = files[i];
        // Check the file type.
        if(!file.type.match("text*")) continue;
        console.log("file type: " + file.type);


        // Add the file to the request.
        var formData = new FormData();
        formData.append('file', file, file.name);

        xhr = new XMLHttpRequest();
        xhr.open('POST', '/tpdlc2017/upload', true);
        xhr.onload = function () {
          if (xhr.status === 200) {

          } else {
            alert('An error occurred!');
          }
        };
        xhr.send(formData);
        $.post('/tpdlc2017/upload', formData, function(data){
              console.log("resonse: " + data);
           }
        );
    }
}
});*/
(function() {
var files;
// Add events
$('input[type=file]').on('change', prepareUpload);
$('form').on('submit', uploadFiles);
// Grab the files and set them to our variable
function prepareUpload(event)
{
    files = event.target.files;
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
    this.run = function(){
        var _this = this;
        if(this.queue.length>0){
            var data = this.queue.pop();

            var xhr = new XMLHttpRequest();
            xhr.open(this.method, this.url, true);
            xhr.onload = function () {
              if (xhr.status === 200) {
                console.log("done");
              } else {
                console.log("error");
              }
              _this.run();
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
        // Check the file type.
        if(!file.type.match("text*")) continue;
        console.log("file type: " + file.type);


        // Add the file to the request.
        var formData = new FormData();
        formData.append('file', file, file.name);
        manager.add(formData);
        /*var xhr = new XMLHttpRequest();
        xhr.open('POST', '/tpdlc2017/upload', true);
        xhr.onload = function () {
          if (xhr.status === 200) {

          } else {
            alert('An error occurred!');
          }
        };
        xhr.send(formData);*/


    }
    manager.run();
}

})();
