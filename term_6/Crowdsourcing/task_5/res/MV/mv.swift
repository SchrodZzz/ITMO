import Foundation

let tlkAgg2 = TSVParser("../data/TlkAgg2/golden_labels.tsv")
let tlkAgg5 = TSVParser("../data/TlkAgg5/golden_labels.tsv")

let tsvProcesser = TSVProcesser(shouldCreateTSV: true)

let tokenProcesser = { clojure in
    let tokenReader = Tokenizer(data)
    while tokenReader.hasNextToken {
        clojure?()
    }
}

tsvProcesser.calculate(.MV, from: tlkAgg2, with: tokenProcesser, dataIsMultiple: false)
tsvProcesser.calculate(.MV, from: tlkAgg5, with: tokenProcesser, dataIsMultiple: true)