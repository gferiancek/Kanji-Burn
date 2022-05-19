package com.gavinferiancek.core_ui

sealed class SrsStageData(
    open val uiValue: String,
    open val imageResource: Int,
) {
    data class Initiate(
        override val uiValue: String = "Initiate",
        override val imageResource: Int = R.drawable.apprentice,
    ) : SrsStageData(uiValue, imageResource)

    data class ApprenticeI(
        override val uiValue: String = "Apprentice I",
        override val imageResource: Int = R.drawable.apprentice,
    ) : SrsStageData(uiValue, imageResource)

    data class ApprenticeII(
        override val uiValue: String = "Apprentice II",
        override val imageResource: Int = R.drawable.apprentice,
    ) : SrsStageData(uiValue, imageResource)

    data class ApprenticeIII(
        override val uiValue: String = "Apprentice III",
        override val imageResource: Int = R.drawable.apprentice,
    ) : SrsStageData(uiValue, imageResource)

    data class ApprenticeIV(
        override val uiValue: String = "Apprentice IV",
        override val imageResource: Int = R.drawable.apprentice,
    ) : SrsStageData(uiValue, imageResource)

    data class GuruI(
        override val uiValue: String = "Guru I",
        override val imageResource: Int = R.drawable.guru,
    ) : SrsStageData(uiValue, imageResource)

    data class GuruII(
        override val uiValue: String = "Guru II",
        override val imageResource: Int = R.drawable.guru,
    ) : SrsStageData(uiValue, imageResource)

    data class Master(
        override val uiValue: String = "Master",
        override val imageResource: Int = R.drawable.master,
    ) : SrsStageData(uiValue, imageResource)

    data class Enlightened(
        override val uiValue: String = "Enlightened",
        override val imageResource: Int = R.drawable.enlightened,
    ) : SrsStageData(uiValue, imageResource)

    data class Burned(
        override val uiValue: String = "Burned",
        override val imageResource: Int = R.drawable.burned,
    ) : SrsStageData(uiValue, imageResource)

    data class Unknown(
        override val uiValue: String = "",
        override val imageResource: Int = -1
    ) : SrsStageData(uiValue, imageResource)
}
