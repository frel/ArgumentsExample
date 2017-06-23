## Example project for handling arguments in Android 

_Disclaimer: This is not the only way arguments can be handled, simply an example._

### The app

The app navigates to a fragment when the button is pressed. Each time you press the label view in the 
fragment, the view and the arguments are updated. In order to test restoring from state simply enable 
"Don't keep activities" in the Developer options, and then minimize and maximize the app.

#### Short primer


- Fragments should not pass data through the constructor. It should be passed by using [arguments]
- `setArguments()` can only be called before the Fragment is active
- Android can kill the app when it is not in foreground in order to save resources. When opening the 
app again android calls `onCreate` and passes a `savedInstanceState` bundle. In addition any arguments 
that was originally set when creating the fragment, can be retrieved. 

#### Navigation

For small projects, navigation in Android is trivial. However, when the project grows in size it becomes 
harder and harder to handle navigation. Deep-linking adds complexity as well. 

There might be a need for having some data forwarded every time you navigate, such as where you're coming 
from and logging information, and some data modified/created depending on various reasons.

This can be handled by having one containing bundle that contains other bundles. In this example 
the containing bundle is what's passed to the fragment.

#### Instances of Arguments

Since arguments are stored in bundles it makes it impractical to modify them, as you would need to 
know the keys and value types etc. In short, it makes sense to create instances that are used 
to access the data, and then build a bundle from the instance when required.

In this example there's the abstract class `Arguments` which declare the abstract method `makeBundle`
which is responsible for building a bundle based on the the data in the Arguments instance. The subclass
`DataArguments` implements the method and stores its data. Each `Arguments` subclass must also implement 
a constructor that takes the single parameter `Bundle`. This is then used to create `Arguments` instances
from the bundle. 

#### Setting up the BaseFragment with Arguments

The abstract class `BaseFragment` declares one abstract method `getNavigationArgs()` and implements 
`getNavigationArgs(Class<T> clazz)` and `setNavigationArgs()`. 
 
The subclass implements `getNavigationArgs()` and changes the return type to `Arguments` instance e.g.
`DataArguments`

Example:

```
    @Override
    public DataArguments getNavigationArgs() {
        return getNavigationArgs(DataArguments.class);
    }
```

Every time the arguments should be accessed, this method should be used. And when it's time to set new arguments
`setNavigationArgs()` is used.

For more information on the methods inspect the implementation. 

##### Do not use `setArguments()`

The one caveat is that `setArguments()` should never be called explicitly. Always use `setNavigationArgs()`.

This is required for two main reasons:

1. The field that holds the Arguments instance `mArgs` must also be updated in order to stay consistent. 
 
2. Calling `setArguments()` after the fragment is in an active state will throw an exception.
This limitation makes sense for initially creating fragments from arguments, but not when updating 
them and restoring from state.

It is possible to retrieve, clear and re-populate the arguments, however. And all of this is handled by `setNavigationArgs()`
#### Do not store too much data in the bundle!

Due to the binder transaction limit of 1MB the system can throw an [TransactionTooLargeException] if too much data
is stored in the bundle.

One solution for this is to store the data as POJO's in a cache and then storing the keys in 
the Arguments.

The handling of restoring from the cache can easily be implemented in the Arguments "bundle constructor"
and the storing to cache in `makeBundle()`.


[arguments]: https://developer.android.com/reference/android/app/Fragment.html#setArguments(android.os.Bundle)
[TransactionTooLargeException]: https://developer.android.com/reference/android/os/TransactionTooLargeException.html
