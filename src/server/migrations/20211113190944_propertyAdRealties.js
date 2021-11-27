
exports.up = function (knex) {
    return knex.schema.createTable("propertyAdRealties", (tbl) => {
        tbl.increments() //id primary key AI
        tbl.integer('property_ad_id').notNullable()
            .references('id').inTable('propertyAd').onDelete('CASCADE').onUpdate('CASCADE')
        tbl.integer('number_of_rooms').notNullable()
        tbl.integer('number_of_bathrooms').notNullable()
        tbl.decimal('surface').notNullable()
        tbl.text('floor_plan_url', 255).notNullable()
    });
};

exports.down = function (knex) {
    return knex.schema.dropTableIfExists('propertyAdRealties');
};
