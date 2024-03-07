<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HelloWorld</title>
</head>
<body>
<h2>File Upload</h2>
<form action="http://127.0.0.1:2000/file/upload" method="post" enctype="multipart/form-data">
    Select file to upload:
    <input type="file" name="file" id="file">
    <input type="submit" value="Upload Image" name="submit">
</form>
</body>
</html>