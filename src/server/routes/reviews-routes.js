const express = require('express');
const Reviews = require('../models/Reviews');
const router = express.Router();

// TODO add DELs

router.get('/realtyReviews/:data', (req, res) => {
    const { property_ad_id } = req.params;
    Reviews.readUserToRealtyByPropertyId(property_ad_id)
        .then(rev => {
            res.status(200).json(rev)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.get('/userReviews/:data', (req, res) => {
    const { user_id } = req.params;
    Reviews.readRealtorToUserByUserId(user_id)
        .then(rev => {
            res.status(200).json(rev)
        })
        .catch(error => {
            res.status(500).json({ message: "Server Error" })
        })
});

router.post('/userToRealty', (req, res) => {
    const data = req.body;
    var { reviewer_id, property_ad_id, date, stars, contents, response, response_date } = data

    response = null
    response_date = null

    if (!(reviewer_id && property_ad_id && date && stars && contents)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.createUserToRealty(data)
            .then(rev => {
                res.status(200).json(rev);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

router.post('/realtorToUser', (req, res) => {
    const data = req.body;
    var { realtor_id, user_id, date, recommends, contents } = data

    if (!(realtor_id && user_id && date && recommends && contents)) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Reviews.createRealtorToUser(data)
            .then(rev => {
                res.status(200).json(rev);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    }
});

module.exports = router;