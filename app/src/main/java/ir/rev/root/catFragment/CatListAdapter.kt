package ir.rev.root.catFragment

import ir.rev.root.R
import ir.rev.vmadapter.ViewModelAdapter

class CatListAdapter : ViewModelAdapter(Mode.VIEW_MODEL_MERGE) {

    init {
        cell(R.layout.item_black_cat, itemChecker = BlackCat)
        cell(R.layout.item_orange_cat, itemChecker = OrangeCat)
        cell(R.layout.item_white_cat, itemChecker = WhiteCat)
        cell(R.layout.item_other_cat, itemChecker = OtherCat)
    }
}