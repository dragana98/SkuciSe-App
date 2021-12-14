var fs = require("fs");

var obj = {dat: []}

fs.readFile("image.jpeg", "base64", function (err, data) {
    if (err) throw err;
    console.log(data);
});
