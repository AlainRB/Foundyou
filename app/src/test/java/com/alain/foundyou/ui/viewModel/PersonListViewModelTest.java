package com.alain.foundyou.ui.viewModel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.alain.foundyou.data.network.model.Person;
import com.alain.foundyou.domain.PersonRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PersonListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private PersonRepository personRepository;

    @Mock
    private Observer<Boolean> isLoadingObserver;

    @Mock
    private Observer<String> errorObserver;

    private PersonListViewModel viewModel;

    @Before
    public void setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        when(personRepository.getPersons()).thenReturn(Flowable.just(Collections.singletonList(mock(Person.class))));
    }

    @After
    public void tearDown() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void testRefreshData_Success() {
        when(personRepository.refreshPersons()).thenReturn(Completable.complete());
        viewModel = new PersonListViewModel(personRepository);
        viewModel.isLoading.observeForever(isLoadingObserver);

        viewModel.refreshData();

        verify(personRepository).refreshPersons();

        InOrder inOrder = inOrder(isLoadingObserver);
        inOrder.verify(isLoadingObserver).onChanged(true);
        inOrder.verify(isLoadingObserver).onChanged(false);
        viewModel.isLoading.removeObserver(isLoadingObserver);
    }

    @Test
    public void testRefreshData_Error() {
        String errorMessage = "Network Error";
        Throwable throwable = new Throwable(errorMessage);
        when(personRepository.refreshPersons()).thenReturn(Completable.error(throwable));
        viewModel = new PersonListViewModel(personRepository);
        viewModel.error.observeForever(errorObserver);

        viewModel.refreshData();

        verify(personRepository).refreshPersons();

        verify(errorObserver).onChanged(errorMessage);
        viewModel.error.removeObserver(errorObserver);
    }
}
