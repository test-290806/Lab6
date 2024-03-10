package server.Commands;

import common.Collection.Worker;
import common.Exceptions.InvalidDataException;
import common.Exceptions.WrongAmountOfArgumentsException;
import common.ICommand;
import common.UserCommand;
import common.requests.ExecuteCommandResponce;
import common.requests.ResultState;
import server.Controllers.CollectionController;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class with realization of remove_greater command
 * <p>This command is used to remove all elements which are greater than given
 * @see UserCommand
 * @see ICommand
 */
public class RemoveGreaterCommand extends UserCommand {
    /**
     * Controller of collection which is used to remove elements
     */
    private CollectionController collectionController;

    private Worker worker;
    /**
     * RemoveGreaterCommand constructor
     * <p> Firstly it initializes super constructor by command name, arguments and description
     * @param collectionController
     */
    public RemoveGreaterCommand(CollectionController collectionController) {
        super("remove_greater", "{element}", "remove all elements which are greater than given");
        this.collectionController = collectionController;
    }

    /**
     * Method to complete remove_greater command
     * <p>It reads element to compare with and then removes elements which are greater that it
     * <p>In the end it prints number of deleted elements
     * <p>If collection is empty element is not read (except script mode)
     *
     * @return
     * @throws InvalidDataException If an error occurred while reading
     */
    @Override
    public ExecuteCommandResponce execute() {
        int elementsRemoved = this.collectionController.removeGreater(worker);
        return new ExecuteCommandResponce(ResultState.SUCCESS,
                String.format("Successfully removed %d elements!", elementsRemoved));
    }

    @Override
    public void initCommandArgs(ArrayList<Serializable> arguments) {
        this.worker = (Worker) arguments.get(0);
    }
}