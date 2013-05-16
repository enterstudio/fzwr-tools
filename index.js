var orm    = require('orm')
  , nano   = require('nano')
  , excel  = require('excel')
  , _      = require('underscore-contrib')
  , o_o    = require('lodash')
  , path   = require('path')
  , moment = require('moment')
  , argv   = require('optimist').argv
  ;

// orm.connect('fzmv.sqlite', function (err, db) {
//   var Player = db.define('player', { name: String
//                                    , country: String
//   })
// })

var entriesFile = argv.xlsx
var database    = argv.db
console.log(entriesFile, database)

excel(entriesFile, function (err, data) {
  if (err) throw err;

  var column_names = data[0]
  var entries      = data.slice(1)

  // _.each(entries, function (row) {
  //   console.log(_.object(column_names, row))
  // })

  // _.map(entries, function (row) { return _.object(column_names, row)})
  //           .groupBy('type')
  //           .tap(console.log)

  var objectify    = _.partial(_.object, column_names)
  var objectifyMap = _.partial(_.flip2(_.map), objectify)
  var groupTypes   = _.partial(_.flip2(_.groupBy), 'type')

  var entries = _.pipeline(
    objectifyMap, groupTypes
  )(entries)

  console.log(entries)

  var courses = entries['Course']
  var flaps   = entries['Laps']
})

var writeToSqlite  = function (db) {
  if (err) throw err;

  var Course = db.define("course", {
    shortname: String,
    cup:       String,
    fullname:  String
  }, {
    id: 'shortname'
  })
  Course.sync()

  var Player = db.define("player", {
    name:    String,
    country: String
  })
  Player.sync()

  var Record = db.define("record", {
    time: Number,
    date: Date,
    ship: String,
    gp_mode: Boolean,
    confirmed: Boolean
  })
  Record.hasOne('course', Course, { required: true })
  Record.hasOne('player', Player, { required: true })
  Record.sync()
}