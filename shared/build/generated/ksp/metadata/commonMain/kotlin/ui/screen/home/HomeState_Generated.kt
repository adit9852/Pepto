// This is auto-generated file by Mutekt(https://github.com/PatilShreyas/mutekt)
package ui.screen.home

import dev.shreyaspatil.mutekt.core.AtomicExecutor
import dev.shreyaspatil.mutekt.core.MutektMutableState
import kotlin.Boolean
import kotlin.Nothing
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

/**
 * Mutable state model for [HomeState]
 */
public interface MutableHomeState : HomeState, MutektMutableState<HomeState, MutableHomeState> {
  public override var isLoading: Boolean

  public override var posts: List<HomeState.Post>

  public override var errorMessage: String?
}

private data class ImmutableHomeState(
  public override val isLoading: Boolean,
  public override val posts: List<HomeState.Post>,
  public override val errorMessage: String?,
) : HomeState

private class MutableHomeStateImpl(
  isLoading: Boolean,
  posts: List<HomeState.Post>,
  errorMessage: String?,
) : MutableHomeState {
  private val _atomicExecutor: AtomicExecutor = AtomicExecutor()

  private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(isLoading)

  public override var isLoading: Boolean
    get() = _isLoading.value
    set(`value`) {
      _isLoading.value = value
    }

  private val _posts: MutableStateFlow<List<HomeState.Post>> = MutableStateFlow(posts)

  public override var posts: List<HomeState.Post>
    get() = _posts.value
    set(`value`) {
      _posts.value = value
    }

  private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(errorMessage)

  public override var errorMessage: String?
    get() = _errorMessage.value
    set(`value`) {
      _errorMessage.value = value
    }

  private val _immutableStateFlowImpl: StateFlow<HomeState> = object : StateFlow<HomeState> {
    public override val replayCache: List<HomeState>
      get() = listOf(value)

    public override val `value`: HomeState
      get() = ImmutableHomeState(
        isLoading = _isLoading.value,
        posts = _posts.value,
        errorMessage = _errorMessage.value,
      )

    public override suspend fun collect(collector: FlowCollector<HomeState>): Nothing =
        coroutineScope {
        combine(_atomicExecutor.executing, _isLoading, _posts, _errorMessage) { params ->
            val isUpdating = params[0] as Boolean
            if (!isUpdating) {
              value
            }
            else {
              null
            }
        }
        .filterNotNull()
        .stateIn(this)
        .collect(collector)
    }

  }


  public override fun asStateFlow(): StateFlow<HomeState> = _immutableStateFlowImpl

  public override fun update(mutate: MutableHomeState.() -> Unit): Unit {
    _atomicExecutor.execute { mutate() }
  }
}

/**
 * Creates an instance of state model [MutableHomeState]
 */
public fun MutableHomeState(
  isLoading: Boolean,
  posts: List<HomeState.Post>,
  errorMessage: String?,
): MutableHomeState = MutableHomeStateImpl(isLoading, posts, errorMessage)

/**
 * Creates an instance of state model [HomeState]
 */
public fun HomeState(
  isLoading: Boolean,
  posts: List<HomeState.Post>,
  errorMessage: String?,
): HomeState = ImmutableHomeState(isLoading, posts, errorMessage)

/**
 * Creates a mutable [HomeState] instance from this state model
 */
public fun HomeState.mutable(): MutableHomeState = MutableHomeState(isLoading, posts, errorMessage)
