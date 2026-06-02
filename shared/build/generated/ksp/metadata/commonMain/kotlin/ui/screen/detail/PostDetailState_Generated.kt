// This is auto-generated file by Mutekt(https://github.com/PatilShreyas/mutekt)
package ui.screen.detail

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
 * Mutable state model for [PostDetailState]
 */
public interface MutablePostDetailState : PostDetailState,
    MutektMutableState<PostDetailState, MutablePostDetailState> {
  public override var isLoading: Boolean

  public override var post: PostDetailState.Post?

  public override var errorMessage: String?
}

private data class ImmutablePostDetailState(
  public override val isLoading: Boolean,
  public override val post: PostDetailState.Post?,
  public override val errorMessage: String?,
) : PostDetailState

private class MutablePostDetailStateImpl(
  isLoading: Boolean,
  post: PostDetailState.Post?,
  errorMessage: String?,
) : MutablePostDetailState {
  private val _atomicExecutor: AtomicExecutor = AtomicExecutor()

  private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(isLoading)

  public override var isLoading: Boolean
    get() = _isLoading.value
    set(`value`) {
      _isLoading.value = value
    }

  private val _post: MutableStateFlow<PostDetailState.Post?> = MutableStateFlow(post)

  public override var post: PostDetailState.Post?
    get() = _post.value
    set(`value`) {
      _post.value = value
    }

  private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(errorMessage)

  public override var errorMessage: String?
    get() = _errorMessage.value
    set(`value`) {
      _errorMessage.value = value
    }

  private val _immutableStateFlowImpl: StateFlow<PostDetailState> = object :
      StateFlow<PostDetailState> {
    public override val replayCache: List<PostDetailState>
      get() = listOf(value)

    public override val `value`: PostDetailState
      get() = ImmutablePostDetailState(
        isLoading = _isLoading.value,
        post = _post.value,
        errorMessage = _errorMessage.value,
      )

    public override suspend fun collect(collector: FlowCollector<PostDetailState>): Nothing =
        coroutineScope {
        combine(_atomicExecutor.executing, _isLoading, _post, _errorMessage) { params ->
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


  public override fun asStateFlow(): StateFlow<PostDetailState> = _immutableStateFlowImpl

  public override fun update(mutate: MutablePostDetailState.() -> Unit): Unit {
    _atomicExecutor.execute { mutate() }
  }
}

/**
 * Creates an instance of state model [MutablePostDetailState]
 */
public fun MutablePostDetailState(
  isLoading: Boolean,
  post: PostDetailState.Post?,
  errorMessage: String?,
): MutablePostDetailState = MutablePostDetailStateImpl(isLoading, post, errorMessage)

/**
 * Creates an instance of state model [PostDetailState]
 */
public fun PostDetailState(
  isLoading: Boolean,
  post: PostDetailState.Post?,
  errorMessage: String?,
): PostDetailState = ImmutablePostDetailState(isLoading, post, errorMessage)

/**
 * Creates a mutable [PostDetailState] instance from this state model
 */
public fun PostDetailState.mutable(): MutablePostDetailState = MutablePostDetailState(isLoading,
    post, errorMessage)
