(ns clojurize.fzmv
  (:require [dk.ative.docjure.spreadsheet :as dj]))

(def -file "../history.xlsx")
(def -columns [:A :B :C :D :E :F :G])
(def -sheet-name "WRs")

(defn sheet
  [file]
  (->> file
       (dj/load-workbook)
       (dj/select-sheet "WRs")))

(defn column-names
  [headers]
  (->> headers
       (dj/cell-seq)
       (map dj/read-cell)))

(defn map-names-to-columns
  [names columns]
  (zipmap columns names))

(defn headers-mapping
  [[headers & rows]]
  (let [names (column-names headers)
        records (dj/cell-seq rows)]
    (map-names-to-columns -columns names)))

(defn world-records
  [])

(headers-mapping (dj/row-seq (sheet -file)))


