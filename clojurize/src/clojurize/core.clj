(ns clojurize.core
  (:require [dk.ative.docjure.spreadsheet :as dj]
            [clojurize.fzmv :as mv]))

(def rows
  (->> (dj/load-workbook "../history.xlsx")
       (dj/select-sheet "WRs")
       (dj/row-seq)))

(defn mapped-rows
  [[headers & rows]]
  (let [columns (->> headers
                     (dj/cell-seq)
                     (map dj/read-cell))]
       (zipmap [:A :B :C :D :E :F :G] columns)))


(mapped-rows rows)

(defn wr-rows
      [worksheet]
      (dj/select-columns {:A :course
                          :B :type
                          :C :time
                          :D :player
                          :E :date
                          :F :ship
                          :G :mode} worksheet))

(def wr-sheet
  (->> (dj/load-workbook "../history.xlsx")
       (dj/select-sheet "WRs")))


((wr-rows wr-sheet) 0)