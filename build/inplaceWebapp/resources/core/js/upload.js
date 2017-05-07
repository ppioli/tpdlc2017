var form = document.getElementById('file-form');
var fileSelect = document.getElementById('file-select');
var uploadButton = document.getElementById('upload-button');

form.onsubmit = function(event) {
  event.preventDefault();
  uploadButton.innerHTML = 'Uploading...';
  var formData = new FormData();
  var files = fileSelect.files;
  for (var i = 0; i < files.length; i++) {
    var file = files[i];

    // Check the file type.
    if(!file.type.match("text*")) continue;
    console.log("file type: " + file.type);


    // Add the file to the request.
    formData.append('file', file, file.name);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/tpdlc2017/upload', true);
    xhr.onload = function () {
      if (xhr.status === 200) {

      } else {
        alert('An error occurred!');
      }
    };
    xhr.send(formData);
  }
}


function add_api_call_to_queue(qname, api_url) {
    $(document).queue(qname, function() {
        $.ajax({
            type     : 'POST',
            async    : true,
            url      : api_url,
            dataType : 'form',
            success  : function(data, textStatus, jqXHR) {

                $(document).dequeue(qname);
            }
        });
    });
}