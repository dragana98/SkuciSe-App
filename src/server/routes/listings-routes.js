const express = require('express');
const Listings = require('../models/Listings');
const router = express.Router();

router.get('/all', (req, res) => {
    Listings.readAll()
        .then(realties => {
            res.status(200).json(realties)
        })
        .catch(err => {
            console.log(err);
            res.status(500).json(err)
        })
});

router.get('/:id', (req, res) => {
    const { id } = req.params;

    if (id == undefined) {
        res.status(400).json({ message: "Missing information" })
    } else {
        Listings.readByPropertyId(id)
            .then(realty => {
                res.status(200).json(realty);
            })
            .catch(err => {
                res.status(500).json(err);
            })
    }
});

router.post('/', (req, res) => {
    var pass = true

    const data = req.body
    const { name, price, deposit, realtor_id, description, city, postal_code, street_address, leasable, unified, images, amenities, property_ad_realties } = data

    pass &&= (name && realtor_id && description && city && postal_code && street_address)

    pass &&= (leasable != undefined) && (unified != undefined)

    if (unified != 0) {
        pass &&= price

        if(leasable != 0) {
            pass &&= deposit
        }
    }

    try {
        pass &&= images.length > 0
        
        pass &&= property_ad_realties.length > 0

        property_ad_realties.forEach(realty => {
            if(unified == 0) {
                pass &&= realty['price']
                
                if (leasable != 0) {
                    pass &&= realty['deposit']
                }
            } 
        });

    } catch (e) {
        pass = false
    }

    if (pass) {
        Listings.create(data)
            .then(realty => {
                res.status(200).json(realty);
            })
            .catch(err => {
                console.log(err);
                res.status(500).json(err);
            })
    } else {
        res.status(400).json({ message: "Missing information" })
    }
});

router.delete('/:id', (req, res) => {
    const { id } = req.params;

    Listings.del(id)
        .then(num => {
            res.status(200).json(num);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json(err);
        })
});

module.exports = router;