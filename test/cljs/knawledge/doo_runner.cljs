(ns knawledge.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [knawledge.core-test]))

(doo-tests 'knawledge.core-test)

