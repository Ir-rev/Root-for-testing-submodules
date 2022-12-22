package ir.rev.root.catFragment

import androidx.databinding.ObservableField
import ir.rev.vmadapter.ItemChecker

sealed class BaseCat(
    val name: String,
    val group: String
) {

    abstract val titleText: ObservableField<String>
}

class BlackCat(name: String, group: String) : BaseCat(name, group) {

    override val titleText: ObservableField<String> = ObservableField("я черный кот по имени кот-$name")

    companion object : ItemChecker.ForViewModelMerge<BlackCat>() {

        override fun areItemsTheSame(left: BlackCat, right: BlackCat) =
            left.name == right.name

        override fun merge(left: BlackCat, right: BlackCat) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}

class OrangeCat(name: String, group: String) : BaseCat(name, group) {

    override val titleText: ObservableField<String> = ObservableField("я не кот,а К-О-Т-$name из группы $group")

    companion object : ItemChecker.ForViewModelMerge<OrangeCat>() {

        override fun areItemsTheSame(left: OrangeCat, right: OrangeCat) =
            left.name == right.name

        override fun merge(left: OrangeCat, right: OrangeCat) {
            if (left === right) return
            left.titleText.set(right.titleText.get())
        }
    }
}

class WhiteCat(name: String, group: String) : BaseCat(name, group) {

    override val titleText: ObservableField<String> = ObservableField("white power number $name")

    /**
     * оставил тут просто что бы показать что в разные наследники можно что нибудь засунуть интересное
     */
    val magicValue = "$name $group"

    companion object : ItemChecker.ForViewModelMerge<WhiteCat>() {

        override fun areItemsTheSame(left: WhiteCat, right: WhiteCat) =
            left.name == right.name

        override fun merge(left: WhiteCat, right: WhiteCat) {
            if (left === right) return
            left.titleText.set(right.titleText.get())

        }
    }
}

class OtherCat(name: String, group: String) : BaseCat(name, group) {

    override val titleText: ObservableField<String> = ObservableField("я что то иное номер $name")

    companion object : ItemChecker.ForViewModelMerge<OtherCat>() {

        override fun areItemsTheSame(left: OtherCat, right: OtherCat) =
            left.name == right.name

        override fun merge(left: OtherCat, right: OtherCat) {
            if (left === right) return
            left.titleText.set(right.titleText.get())

        }
    }
}

