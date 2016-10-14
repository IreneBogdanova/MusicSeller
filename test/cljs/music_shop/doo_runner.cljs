(ns music_shop.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [music_shop.core-test]))

(doo-tests 'music_shop.core-test)

