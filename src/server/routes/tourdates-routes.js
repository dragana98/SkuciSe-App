const express = require('express');
const TourDates = require('../models/TourDates');
const router = express.Router();

//TODO populate reserved_by, reserved_at, del

router.get('/:data', (req, res) => {
    const { property_ad_id } = req.params;
    TourDate.read(property_ad_id)
        .then(avdate => {
            res.status(200).json(avdate)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.post('/', (req, res) => {
    var data = req.body;
    var { property_ad_id, date } = data

    data.username = req.decodedToken.username;

    if (!(property_ad_id && date)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        TourDate.create(data)
            .then(avdate => {
                res.status(200).json(avdate);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

module.exports = router;