package ir.rev.root.catFragment

import ir.rev.twoWayActionsBus.TwoWayAction

/**
 * Описание возможных сайд эфектов
 */
sealed class CatListViewModelAction : TwoWayAction()

/**
 * Показать сообщение об ошибке.
 * @property message текст сообщения
 */
class ShowToast(val message: String): CatListViewModelAction()

/**
 * Удаляет котенка=(
 * @property catNumber текст сообщения
 */
class DeleteCat(val catNumber: String): CatListViewModelAction()

