db.holiday.find({duration:{$type:1}}).forEach(function (t) {
 t.duration= new NumberInt(t.duration);
 db.holiday.save(t);
})