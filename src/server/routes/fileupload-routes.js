const express = require('express');
const router = express.Router();
const fs = require('fs');

router.post('/', (req, res) => {
    var data = req.body;

    const { hexdata } = data

    if (!hexdata) {
        res.status(400).json({ message: "Missing information" })
    } else {
        const fileContents = Buffer.from(hexdata, 'base64')

        var filename = Date.now().toString()  + '.jpg'

        fs.writeFile('./img/' + filename, fileContents, (err) => {
            if (err) {
                res.status(400).json({ err })
            }
            else {
                res.status(200).json({ filename })
            }
        })
    }
});

module.exports = router;
