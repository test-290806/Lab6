package server.Commands;

import common.Collection.Worker;
import common.Exceptions.*;
import common.ICommand;
import common.UserCommand;
import common.requests.ExecuteCommandResponce;
import common.requests.ResultState;
import server.Controllers.CollectionController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class with realization of update command
 * <p>This command is used to update value of collection element which id is equal to given
 * @see UserCommand
 * @see ICommand
 */
public class UpdateByIdCommand extends UserCommand {
    /**
     * Controller of collection which is used to update element
     */
    private CollectionController collectionController;
    /**
     * id of element to update
     */
    private long id;
    Worker worker;

    /**
     * UpdateByIdCommand constructor
     * <p> Firstly it initializes super constructor by command name, arguments and description
     * @param collectionController
     */
    public UpdateByIdCommand(CollectionController collectionController) {
        super("update", "id {element}", "update value of collection element which id is equal to given");
        this.collectionController = collectionController;
    }

    /**
     * Method to complete update command
     * <p>It reads new element from user and then updates value of element with given id inside collection
     *
     * @return
     * @throws NoSuchElementException is element with given id was not found
     */
    @Override
    public ExecuteCommandResponce execute() {
        this.collectionController.update(id, worker);
        return new ExecuteCommandResponce(ResultState.SUCCESS,
                "Element updated successfully!");
    }

    @Override
    public void initCommandArgs(ArrayList<Serializable> arguments) {
        this.id = (long) arguments.get(0);
        this.worker = (Worker) arguments.get(1);
    }
}