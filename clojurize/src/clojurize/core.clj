(ns clojurize.core
  (:require [dk.ative.docjure.spreadsheet :as dj]))


(->> (dj/load-workbook "../history.xlsx")
     (dj/select-sheet "WRs")
     (dj/select-columns {:A :course
                         :B :type
                         :C :time
                         :D :player
                         :E :date
                         :F :ship
                         :G :mode}))