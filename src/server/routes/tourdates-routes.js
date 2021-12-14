const express = require('express');
const TourDate = require('../models/TourDates');
const router = express.Router();

router.get('/:property_ad_id', (req, res) => {
    const { property_ad_id } = req.params;

    if (!property_ad_id) {
        res.status(500).json({ message: "Missing information" })
    } else {
        TourDate.read(property_ad_id)
            .then(avdate => {
                res.status(200).json(avdate)
            })
            .catch(error => {
                res.status(500).json({ message: "Server Error"})
            })
    }
});

router.get('/all/:property_ad_id', (req, res) => {
    const { property_ad_id } = req.params;

    if (!property_ad_id) {
        res.status(500).json({ message: "Missing information" })
    } else {
        TourDate.readAll(property_ad_id)
            .then(avdate => {
                res.status(200).json(avdate)
            })
            .catch(error => {
                res.status(500).json({ message: "Server Error"})
            })
    }
});

router.post('/', (req, res) => {
    var data = req.body;
    var { property_ad_id, date } = data

    data.username = req.decodedToken.username;

    if (!(property_ad_id && date)) {
        res.status(400).json({ message: "Missing information"})
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

router.post('/reserve', (req, res) => {
    var data = req.body;
    const { tour_id } = data

    data.username = req.decodedToken.username;

    if (!tour_id) {
        res.status(400).json({ message: "Missing information"})
    } else {
        TourDate.schedule(data)
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