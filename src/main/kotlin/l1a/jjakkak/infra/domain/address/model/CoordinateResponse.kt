package l1a.jjakkak.infra.domain.address.model

import l1a.jjakkak.core.domain.address.Coordinate

internal data class CoordinateResponse(
    override val x: String,
    override val y: String
) : Coordinate