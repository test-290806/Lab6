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
 * Class with realization of add command
 * <p>This command is used to add new element to collection
 * @see UserCommand
 * @see ICommand
 */
public class AddCommand extends UserCommand {
    /**
     * Controller of collection which is used to add new element
     */
    private CollectionController collectionController;

    private Worker worker;

    /**
     * AddCommand constructor
     * <p> Firstly it initializes super constructor by command name, arguments and description
     * @param collectionController
     */
    public AddCommand(CollectionController collectionController) {
        super("add", "{element}", "add new element to collection");
        this.collectionController = collectionController;
    }

    @Override
    public void initCommandArgs(ArrayList<Serializable> arguments) {
        this.worker = (Worker) arguments.get(0);
    }

    /**
     * Method to complete add command
     * <p>It reads new element and then adds it to collection
     *
     * @return
     */
    @Override
    public ExecuteCommandResponce execute() {
        collectionController.add(worker);
        return new ExecuteCommandResponce(ResultState.SUCCESS, "Worker added successfully!");
    }
}