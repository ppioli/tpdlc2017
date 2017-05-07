var form = document.getElementById('file-form');
var fileSelect = document.getElementById('file-select');
var uploadButton = document.getElementById('upload-button');

form.onsubmit = function(event) {
  event.preventDefault();
  uploadButton.innerHTML = 'Uploading...';
  var formData = new FormData();
  for (var i = 0; i < files.length; i++) {
    var file = files[i];

    // Check the file type.
    if (!file.type.match('image.*')) {
      continue;
    }

    // Add the file to the request.
    formData.append('files', file, file.name);
  }
}
