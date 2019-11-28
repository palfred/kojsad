package dk.rheasoft.kojsad

import dk.rheasoft.kojsad.gson.GsonObjectAdapter
import dk.rheasoft.kojsad.minimal.MinimalObjectAdapter

public actual var Adapter: JsonAdapter = MinimalObjectAdapter()

